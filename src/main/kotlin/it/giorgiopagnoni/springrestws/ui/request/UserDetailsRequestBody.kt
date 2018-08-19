package it.giorgiopagnoni.springrestws.ui.request

class UserDetailsRequestBody(
        var email: String,
        var password: String,
        var firstName: String,
        var lastName: String
)