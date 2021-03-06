package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.exceptions.UserServiceException
import it.giorgiopagnoni.springrestws.io.entity.PasswordResetTokenEntity
import it.giorgiopagnoni.springrestws.io.repositories.UserRepository
import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import it.giorgiopagnoni.springrestws.io.repositories.PasswordResetTokenRepository
import it.giorgiopagnoni.springrestws.service.UserMailer
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.Utils
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import it.giorgiopagnoni.springrestws.ui.response.ErrorMessages
import org.modelmapper.ModelMapper
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordResetTokenRepository: PasswordResetTokenRepository

    @Autowired
    lateinit var utils: Utils

    @Autowired
    lateinit var userMailer: UserMailer

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("$email not found")

        return User(userEntity.email, userEntity.encryptedPassword, userEntity.emailVerificationStatus,
                true, true, true, ArrayList())
        //return User(userEntity.email, userEntity.encryptedPassword, ArrayList())
    }

    override fun getUser(email: String): UserDto {
        val userEntity = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("$email not found")
        val returnValue = UserDto()
        BeanUtils.copyProperties(userEntity, returnValue)
        return returnValue
    }

    override fun getUserByUserId(userId: String): UserDto {
        val userEntity = userRepository.findByUserId(userId)
                ?: throw UsernameNotFoundException(userId)
        val returnValue = UserDto()
        BeanUtils.copyProperties(userEntity, returnValue)
        return returnValue
    }

    override fun createUser(user: UserDto): UserDto {
        if (userRepository.findByEmail(user.email) != null) {
            throw UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.errorMessage)
        }

        for (addressDto in user.addresses.orEmpty()) {
            addressDto.userDetails = user
            addressDto.addressId = utils.generateAddressId(29)
        }

        val modelMapper = ModelMapper()
        val userEntity = modelMapper.map(user, UserEntity::class.java) as UserEntity
        userEntity.userId = utils.generateUserId(30)
        userEntity.encryptedPassword = bCryptPasswordEncoder.encode(user.password)
        userEntity.emailVerificationToken = utils.generateEmailVerificationToken(userEntity.userId)
        userEntity.emailVerificationStatus = false

        val storedUser: UserEntity = userRepository.save(userEntity)

        val userDto = modelMapper.map(storedUser, UserDto::class.java) as UserDto
        userMailer.sendActivationEmail(userDto)

        return userDto
    }

    override fun updateUser(userId: String, user: UserDto): UserDto {
        val userEntity = userRepository.findByUserId(userId)
                ?: throw UserServiceException(ErrorMessages.NO_RECORD_FOUND.errorMessage)

        // we might not want to update email and password; it depends on the application
        userEntity.firstName = user.firstName
        userEntity.lastName = user.lastName

        val updatedUser: UserEntity = userRepository.save(userEntity)
        val returnValue = UserDto()
        BeanUtils.copyProperties(updatedUser, returnValue)

        return returnValue
    }

    override fun getUsers(page: Int, limit: Int): List<UserDto> {
        val realPage = if (page > 0) page - 1 else 0
        val pageRequest: Pageable = PageRequest.of(realPage, limit)
        val usersPage: Page<UserEntity> = userRepository.findAll(pageRequest)
        val users: List<UserEntity> = usersPage.content

        val returnValue = ArrayList<UserDto>()
        for (userEntity in users) {
            val userDto = UserDto()
            BeanUtils.copyProperties(userEntity, userDto)
            returnValue.add(userDto)
        }

        return returnValue
    }

    @Transactional
    override fun deleteUser(userId: String) {
        val userEntity = userRepository.findByUserId(userId)
                ?: throw UserServiceException(ErrorMessages.NO_RECORD_FOUND.errorMessage)
        userRepository.delete(userEntity)
    }

    override fun verifyEmailToken(token: String): Boolean {
        val userEntity = userRepository.findByEmailVerificationToken(token)
                ?: throw UserServiceException(ErrorMessages.NO_RECORD_FOUND.errorMessage)

        if (utils.hasTokenExpired(token)) return false

        userEntity.emailVerificationToken = null
        userEntity.emailVerificationStatus = true
        userRepository.save(userEntity)
        return true
    }

    override fun requestPasswordReset(email: String): Boolean {
        val userEntity = userRepository.findByEmail(email)
                ?: return false

        val token = utils.generatePasswordResetToken(userEntity.userId)
        val passwordResetTokenEntity = PasswordResetTokenEntity()
        passwordResetTokenEntity.token = token
        passwordResetTokenEntity.userDetails = userEntity
        passwordResetTokenRepository.save(passwordResetTokenEntity)

        userMailer.sendPasswordResetEmail(userEntity.email, token)

        return true
    }
}