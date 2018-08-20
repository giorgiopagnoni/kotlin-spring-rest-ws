package it.giorgiopagnoni.springrestws.ui.controller

import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import it.giorgiopagnoni.springrestws.ui.request.UserDetailsRequestModel
import it.giorgiopagnoni.springrestws.ui.response.UserRest
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping()
    fun getUser(): String = "hi"

    @PostMapping
    fun createUser(@RequestBody userDetails: UserDetailsRequestModel): UserRest {
        val returnValue = UserRest()

        val userDto = UserDto()
        BeanUtils.copyProperties(userDetails, userDto)

        val createdUser = userService.createUser(userDto)
        BeanUtils.copyProperties(createdUser, returnValue)

        return returnValue
    }

    @PutMapping
    fun updateUser(): String = "update"

    @DeleteMapping
    fun deleteUser(): String = "delete"
}