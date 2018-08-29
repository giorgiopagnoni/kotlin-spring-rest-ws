package it.giorgiopagnoni.springrestws.ui.response

open class UserRest(
        var userId: String = "",
        var email: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var addresses: List<AddressRest>? = ArrayList()
)