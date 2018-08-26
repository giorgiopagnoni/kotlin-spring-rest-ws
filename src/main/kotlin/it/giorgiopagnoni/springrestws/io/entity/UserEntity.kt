package it.giorgiopagnoni.springrestws.io.entity

import java.io.Serializable
import javax.persistence.*

@Entity(name = "users")
data class UserEntity(

        @Id @GeneratedValue
        var id: Long? = null,

        @Column(nullable = false)
        var userId: String = "",

        @OneToMany(mappedBy = "userDetails", cascade = [CascadeType.ALL])
        var addresses: List<AddressEntity>? = ArrayList(),

        @Column(nullable = false, length = 100, unique = true)
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