package it.giorgiopagnoni.springrestws.service

import it.giorgiopagnoni.springrestws.shared.dto.AddressDto

interface AddressService {
    fun getAddressesByUserId(userId: String): List<AddressDto>
    fun getAddressByAddressId(addressId: String): AddressDto
}