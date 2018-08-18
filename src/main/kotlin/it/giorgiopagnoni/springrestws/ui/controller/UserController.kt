package it.giorgiopagnoni.springrestws.ui.controller

import it.giorgiopagnoni.springrestws.ui.request.UserDetailsRequestBody
import it.giorgiopagnoni.springrestws.ui.response.UserRest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping()
    fun getUser(): String = "hi"

    @PostMapping
    fun createUser(@RequestBody userDetails: UserDetailsRequestBody): UserRest? {
        return null
    }

    @PutMapping
    fun updateUser(): String = "update"

    @DeleteMapping
    fun deleteUser(): String = "delete"
}