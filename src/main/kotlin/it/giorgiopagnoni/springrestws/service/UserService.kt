package it.giorgiopagnoni.springrestws.service

import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun getUser(email: String): UserDto
    fun getUserByUserId(userId: String): UserDto
    fun createUser(user: UserDto): UserDto
    fun updateUser(userId: String, user: UserDto): UserDto
    fun deleteUser(userId: String)
    fun getUsers(page: Int, limit: Int): List<UserDto>
    fun verifyEmailToken(token: String): Boolean
    fun requestPasswordReset(email: String): Boolean
}