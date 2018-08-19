package it.giorgiopagnoni.springrestws.io.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "users")
data class UserEntity(

        @Id
        @GeneratedValue
        var id: Long? = null,

        @Column(nullable = false)
        var userId: String = "",

        @Column(nullable = false, length = 100)
        var email: String = "",

        @Column(nullable = false, length = 50)
        var firstName: String = "",

        @Column(nullable = false, length = 50)
        var lastName: String = "",

        @Column(nullable = false)
        var encryptedPassword: String = "",

        var emailVerificationToken: String = "",

        @Column(nullable = false, columnDefinition = "boolean default false")
        var emailVerificationStatus: Boolean = false

) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}