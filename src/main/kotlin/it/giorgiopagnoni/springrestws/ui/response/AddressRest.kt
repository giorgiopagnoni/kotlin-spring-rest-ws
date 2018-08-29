package it.giorgiopagnoni.springrestws.ui.response

import org.springframework.hateoas.ResourceSupport

// oper because of methodOn for HATEOAS
open class AddressRest(
        var addressId: String = "",

        var city: String = "",
        var country: String = "",
        var streetName: String = "",
        var postalCode: String = "",
        var type: String = ""
) : ResourceSupport()