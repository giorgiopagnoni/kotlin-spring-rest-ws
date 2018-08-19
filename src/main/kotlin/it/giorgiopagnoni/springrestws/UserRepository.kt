package it.giorgiopagnoni.springrestws

import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long>