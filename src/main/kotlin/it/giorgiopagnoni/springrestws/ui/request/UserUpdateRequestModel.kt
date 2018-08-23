package it.giorgiopagnoni.springrestws.ui.request

import javax.validation.constraints.NotBlank

class UserUpdateRequestModel(
        @field:NotBlank
        var firstName: String = "",

        @field:NotBlank
        var lastName: String = ""
)