package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.io.repositories.UserRepository
import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.Utils
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var utils: Utils

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("$email not found")

        return User(userEntity.email, userEntity.encryptedPassword, ArrayList())
    }

    override fun getUser(email: String): UserDto {
        val userEntity = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("$email not found")
        val returnValue = UserDto()
        BeanUtils.copyProperties(userEntity, returnValue)
        return returnValue
    }

    override fun createUser(userDto: UserDto): UserDto {
        if (userRepository.findByEmail(userDto.email) != null) {
            throw RuntimeException("Record already exists")
        }

        val userEntity = UserEntity()
        BeanUtils.copyProperties(userDto, userEntity)
        userEntity.userId = utils.generateUserId(30)
        userEntity.encryptedPassword = bCryptPasswordEncoder.encode(userDto.password)

        val storedUser: UserEntity = userRepository.save(userEntity)

        val returnValue = UserDto()
        BeanUtils.copyProperties(storedUser, returnValue)

        return returnValue

    }
}