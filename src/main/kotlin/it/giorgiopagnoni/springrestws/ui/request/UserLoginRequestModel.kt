package it.giorgiopagnoni.springrestws.ui.request

data class UserLoginRequestModel(
        var email: String = "",
        var password: String = ""
)