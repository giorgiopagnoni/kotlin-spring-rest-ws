package it.giorgiopagnoni.springrestws.io.repositories

import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByUserId(userId: String): UserEntity?
}