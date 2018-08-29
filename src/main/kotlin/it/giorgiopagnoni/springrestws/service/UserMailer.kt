package it.giorgiopagnoni.springrestws.service

import it.giorgiopagnoni.springrestws.shared.dto.UserDto

interface UserMailer {
    fun sendActivationEmail(user: UserDto)
}