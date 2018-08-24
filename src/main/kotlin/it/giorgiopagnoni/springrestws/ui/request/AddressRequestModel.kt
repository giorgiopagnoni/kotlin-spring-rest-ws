package it.giorgiopagnoni.springrestws.ui.request

class AddressRequestModel(
        var city: String = "",
        var country: String = "",
        var streetName: String = "",
        var postalCode: String = "",
        var type: String = ""
)