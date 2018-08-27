package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.io.entity.AddressEntity
import it.giorgiopagnoni.springrestws.io.repositories.AddressRepository
import it.giorgiopagnoni.springrestws.io.repositories.UserRepository
import it.giorgiopagnoni.springrestws.service.AddressService
import it.giorgiopagnoni.springrestws.shared.dto.AddressDto
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl : AddressService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    override fun getAddressesByUserId(userId: String): List<AddressDto> {
        val userEntity = userRepository.findByUserId(userId)
                ?: throw UsernameNotFoundException(userId)

        val addressEntities: Iterable<AddressEntity> = addressRepository.findAllByUserDetails(userEntity)

        val modelMapper = ModelMapper()
        val returnValue = ArrayList<AddressDto>()
        for (address in addressEntities) {
            returnValue.add(modelMapper.map(address, AddressDto::class.java))
        }

        return returnValue
    }
}