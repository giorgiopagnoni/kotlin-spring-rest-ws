package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.UserRepository
import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.Utils
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var utils: Utils

    override fun createUser(userDto: UserDto): UserDto {
        if (userRepository.findByEmail(userDto.email) != null) {
            throw RuntimeException("Record already exists")
        }

        val userEntity = UserEntity()
        BeanUtils.copyProperties(userDto, userEntity)
        userEntity.userId = utils.generateUserId(30)

        // tmp
        userEntity.encryptedPassword = "ciaoo"
        // end tmp

        val storedUser: UserEntity = userRepository.save(userEntity)

        val returnValue = UserDto()
        BeanUtils.copyProperties(storedUser, returnValue)

        return returnValue

    }
}