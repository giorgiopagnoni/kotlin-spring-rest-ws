package it.giorgiopagnoni.springrestws.ui.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class PasswordResetRequestModel(
        @field:NotBlank @field:Email
        var email: String
)