package it.giorgiopagnoni.springrestws.shared.dto

import java.io.Serializable

class UserDto(
        val email: String,
        var password: String,
        var firstName: String,
        var lastName: String,
        var encryptedPassword: String,
        var emailVerificationToken: String,
        var emailVerificationStatus: Boolean = false,

        val id: Long
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}