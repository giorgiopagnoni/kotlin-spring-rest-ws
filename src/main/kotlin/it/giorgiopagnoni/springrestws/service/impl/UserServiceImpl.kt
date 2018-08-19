package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.UserRepository
import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun createUser(userDto: UserDto): UserDto {
        val userEntity: UserEntity = UserEntity()
        BeanUtils.copyProperties(userDto, userEntity)

        // tmp
        userEntity.encryptedPassword = "ciaoo"
        userEntity.userId = "testuserid"
        // end tmp

        val storedUser: UserEntity = userRepository.save(userEntity)

        val returnValue: UserDto = UserDto()
        BeanUtils.copyProperties(storedUser, returnValue)

        return returnValue

    }
}