package it.giorgiopagnoni.springrestws.ui.controller

import it.giorgiopagnoni.springrestws.exceptions.UserServiceException
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import it.giorgiopagnoni.springrestws.ui.request.UserCreateRequestModel
import it.giorgiopagnoni.springrestws.ui.request.UserUpdateRequestModel
import it.giorgiopagnoni.springrestws.ui.response.ErrorMessages
import it.giorgiopagnoni.springrestws.ui.response.UserRest
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

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
        val returnValue = UserRest()

        // just an example
        // if (userDetails.firstName.isNullOrEmpty()) throw UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.errorMessage)

        val userDto = UserDto()
        BeanUtils.copyProperties(userDetails, userDto)

        val createdUser = userService.createUser(userDto)
        BeanUtils.copyProperties(createdUser, returnValue)

        return returnValue
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

    @DeleteMapping
    fun deleteUser(): String = "delete"
}