package it.giorgiopagnoni.springrestws.io.entity

import java.io.Serializable
import javax.persistence.*

@Entity(name = "password_reset_tokens")
data class PasswordResetTokenEntity(

        @Id @GeneratedValue
        var id: Long = 0,

        var token: String = "",

        @OneToOne @JoinColumn(name = "users_id")
        var userDetails: UserEntity = UserEntity()

) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}