package it.giorgiopagnoni.springrestws

import it.giorgiopagnoni.springrestws.io.entity.UserEntity
import it.giorgiopagnoni.springrestws.io.repositories.UserRepository
import it.giorgiopagnoni.springrestws.service.impl.UserServiceImpl
import junit.framework.TestCase.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.mockito.Mockito.`when` as mWhen


@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    lateinit var userService: UserServiceImpl

    @Mock
    lateinit var userRepository: UserRepository

    @Test
    fun testGetUser() {
        val userEntity = UserEntity()
        userEntity.id = 1L
        userEntity.firstName = "Giorgio"
        userEntity.userId = "sbdfkgbdsjgrjfg"
        userEntity.encryptedPassword = "nfkfgnkrngkrgkrg"

        mWhen(userRepository.findByEmail(anyString())).thenReturn(userEntity)

        val userDto = userService.getUser("bksdfbk@test.com")

        assertNotNull(userDto)
        assertEquals(userEntity.firstName, userDto.firstName)
    }

    @Test(expected = UsernameNotFoundException::class)
    fun testGetUser_UsernameNotFounException() {
        mWhen(userRepository.findByEmail(anyString())).thenReturn(null)
        userService.getUser("bksdfbk@test.com")
    }
}