package it.giorgiopagnoni.springrestws.io.entity

import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import java.io.Serializable
import javax.persistence.*

@Entity(name = "addresses")
data class AddressEntity(
        @Id @GeneratedValue
        var id: Long = 0,

        @Column(length = 30, nullable = false)
        var addressId: String = "",

        @ManyToOne @JoinColumn(name="users_id")
        var userDetails: UserEntity = UserEntity(),

        @Column(length = 20, nullable = false)
        var city: String = "",

        @Column(length = 20, nullable = false)
        var country: String = "",

        @Column(length = 100, nullable = false)
        var streetName: String = "",

        @Column(length = 10, nullable = false)
        var postalCode: String = "",

        @Column(length = 10, nullable = false)
        var type: String = ""
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}