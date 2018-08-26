package it.giorgiopagnoni.springrestws.shared.dto

import java.io.Serializable

data class AddressDto(
        var id: Long = 0,
        var addressId: String = "",
        var userDetails: UserDto = UserDto(),

        var city: String = "",
        var country: String = "",
        var streetName: String = "",
        var postalCode: String = "",
        var type: String = ""
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}