package it.giorgiopagnoni.springrestws.io.repositories

import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByUserId(userId: String): UserEntity?
}