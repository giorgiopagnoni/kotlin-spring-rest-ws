package it.giorgiopagnoni.springrestws.service

import it.giorgiopagnoni.springrestws.shared.dto.UserDto

interface UserService {
    fun createUser(userDto: UserDto): UserDto
}