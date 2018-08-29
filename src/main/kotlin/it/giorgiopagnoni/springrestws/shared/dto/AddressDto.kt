package it.giorgiopagnoni.springrestws.shared.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable

data class AddressDto(
        var id: Long = 0,
        var addressId: String = "",

        @field:JsonIgnore
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