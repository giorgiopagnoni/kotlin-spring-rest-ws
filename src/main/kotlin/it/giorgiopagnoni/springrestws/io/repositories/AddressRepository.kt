package it.giorgiopagnoni.springrestws.io.repositories

import it.giorgiopagnoni.springrestws.io.entity.AddressEntity
import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : CrudRepository<AddressEntity, Long> {
    fun findAllByUserDetails(user: UserEntity): List<AddressEntity>
}