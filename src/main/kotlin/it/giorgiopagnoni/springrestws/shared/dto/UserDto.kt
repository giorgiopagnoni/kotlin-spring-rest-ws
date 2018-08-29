package it.giorgiopagnoni.springrestws.shared.dto

import java.io.Serializable

data class UserDto(
        var id: Long = 0,
        var userId: String = "",
        var addresses: List<AddressDto>? = ArrayList<AddressDto>(),

        var email: String = "",
        var password: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var encryptedPassword: String = "",
        var emailVerificationToken: String? = "",
        var emailVerificationStatus: Boolean = false
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}