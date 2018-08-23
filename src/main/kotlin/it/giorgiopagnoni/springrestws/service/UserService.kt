package it.giorgiopagnoni.springrestws.service

import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(userDto: UserDto): UserDto
    fun updateUser(userId: String, userDto: UserDto): UserDto
    fun getUser(email: String): UserDto
    fun getUserByUserId(userId: String): UserDto
}