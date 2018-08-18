package it.giorgiopagnoni.springrestws.ui.request

class UserDetailsRequestBody(
        val email: String,
        var password: String,
        var firstName: String,
        var lastName: String
)