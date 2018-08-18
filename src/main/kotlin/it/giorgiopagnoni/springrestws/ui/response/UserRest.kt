package it.giorgiopagnoni.springrestws.ui.response

class UserRest(
        val id: Int,
        val email: String,
        var firstName: String,
        var lastName: String
)