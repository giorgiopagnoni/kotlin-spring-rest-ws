package it.giorgiopagnoni.springrestws.io.repositories

import it.giorgiopagnoni.springrestws.io.entity.PasswordResetTokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetTokenRepository : CrudRepository<PasswordResetTokenEntity, Long>