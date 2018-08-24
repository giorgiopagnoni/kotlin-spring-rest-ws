package it.giorgiopagnoni.springrestws.ui.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserCreateRequestModel(
        @field:NotBlank @field:Email
        var email: String = "",

        @field:NotBlank @field:Size(min=8)
        var password: String = "",

        @field:NotBlank
        var firstName: String = "",

        @field:NotBlank
        var lastName: String = "",

        var addresses: List<AddressRequestModel>? = null
)