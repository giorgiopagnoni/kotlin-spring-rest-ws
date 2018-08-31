package it.giorgiopagnoni.springrestws.ui.controller

import it.giorgiopagnoni.springrestws.service.AddressService
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.dto.AddressDto
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import it.giorgiopagnoni.springrestws.ui.request.PasswordResetRequestModel
import it.giorgiopagnoni.springrestws.ui.request.UserCreateRequestModel
import it.giorgiopagnoni.springrestws.ui.request.UserUpdateRequestModel
import it.giorgiopagnoni.springrestws.ui.response.*
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.modelmapper.ModelMapper
import org.modelmapper.TypeToken
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var addressService: AddressService

    @GetMapping(
            path = ["/{userId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun getUser(@PathVariable userId: String): UserRest {
        val returnValue = UserRest()
        val userDto = userService.getUserByUserId(userId)
        BeanUtils.copyProperties(userDto, returnValue)
        return returnValue
    }

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun createUser(@Valid @RequestBody userDetails: UserCreateRequestModel): UserRest {

        // just an example
        // if (userDetails.firstName.isNullOrEmpty()) throw UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.errorMessage)

        val modelMapper = ModelMapper()
        val userDto = modelMapper.map(userDetails, UserDto::class.java) as UserDto

        val createdUser = userService.createUser(userDto)

        return modelMapper.map(createdUser, UserRest::class.java) as UserRest
    }

    @PutMapping(
            path = ["/{userId}"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun updateUser(@PathVariable userId: String, @Valid @RequestBody userDetails: UserUpdateRequestModel): UserRest {
        val returnValue = UserRest()

        val userDto = UserDto()
        BeanUtils.copyProperties(userDetails, userDto)

        val updateddUser = userService.updateUser(userId, userDto)
        BeanUtils.copyProperties(updateddUser, returnValue)

        return returnValue
    }

    @DeleteMapping(
            path = ["/{userId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun deleteUser(@PathVariable userId: String): OperationStatusModel {
        val returnValue = OperationStatusModel(
                RequestOperationName.DELETE.name,
                RequestOperationStatus.SUCCESS.name
        )

        userService.deleteUser(userId)

        return returnValue
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getUsers(
            @RequestParam(value = "page", defaultValue = "1") page: Int,
            @RequestParam(value = "limit", defaultValue = "25") limit: Int
    ): List<UserRest> {

        val users: List<UserDto> = userService.getUsers(page, limit)

        val returnValue = ArrayList<UserRest>()
        for (userDto in users) {
            val userModel = UserRest()
            BeanUtils.copyProperties(userDto, userModel)
            returnValue.add(userModel)
        }

        return returnValue
    }

    @GetMapping(
            path = ["/{userId}/addresses"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json"]
    )
    fun getUserAddresses(@PathVariable userId: String): Resources<AddressRest> {
        val addressesDto: List<AddressDto> = addressService.getAddressesByUserId(userId)
        val listType: java.lang.reflect.Type = object : TypeToken<List<AddressRest>>() {}.type

        val returnValue: List<AddressRest> = ModelMapper().map(addressesDto, listType)
        for (address in returnValue) {
            val addressLink = linkTo(methodOn(UserController::class.java).getUserAddress(userId, address.addressId)).withSelfRel()
            address.add(addressLink)
            val userLink = linkTo(methodOn(UserController::class.java).getUser(userId)).withRel("user")
            address.add(userLink)
        }

        return Resources(returnValue)
    }

    @GetMapping(
            path = ["/{userId}/addresses/{addressId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json"]
    )
    fun getUserAddress(@PathVariable userId: String, @PathVariable addressId: String): Resource<AddressRest> {
        val addressDto = addressService.getAddressByAddressId(addressId)

        val addressesRestModel = ModelMapper().map(addressDto, AddressRest::class.java)
        val addressLink = linkTo(methodOn(UserController::class.java).getUserAddress(userId, addressId)).withSelfRel()
        val addressesLink = linkTo(methodOn(UserController::class.java).getUserAddresses(userId)).withRel("addresses")
        val userLink = linkTo(UserController::class.java).slash(userId).withRel("user")

        addressesRestModel.add(addressLink)
        addressesRestModel.add(userLink)
        addressesRestModel.add(addressesLink)

        return Resource(addressesRestModel)
    }

    @GetMapping(
            path = ["/email-verification"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun verifyEmailToken(@RequestParam(value = "token") token: String): OperationStatusModel {
        val returnValue = OperationStatusModel(RequestOperationName.VERIFY_EMAIL.name)

        returnValue.operationResult = if (userService.verifyEmailToken(token)) {
            RequestOperationStatus.SUCCESS.name
        } else {
            RequestOperationStatus.ERROR.name
        }

        return returnValue
    }

    @PostMapping(
            path = ["/password-reset-request"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun requestPasswordReset(@Valid @RequestBody passwordResetRequestModel: PasswordResetRequestModel): OperationStatusModel {

        val returnValue = OperationStatusModel(RequestOperationName.REQUEST_PASSWORD_RESET.name, RequestOperationStatus.ERROR.name)
        if (userService.requestPasswordReset(passwordResetRequestModel.email)) {
            returnValue.operationResult = RequestOperationStatus.SUCCESS.name
        }

        return returnValue
    }
}