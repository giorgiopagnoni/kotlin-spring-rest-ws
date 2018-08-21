package it.giorgiopagnoni.springrestws.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import it.giorgiopagnoni.springrestws.SpringApplicationContext
import it.giorgiopagnoni.springrestws.service.UserService
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import it.giorgiopagnoni.springrestws.ui.request.UserLoginRequestModel
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
        var authManager: AuthenticationManager
): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val creds: UserLoginRequestModel = ObjectMapper()
                .readValue(request?.inputStream, UserLoginRequestModel::class.java)

        return authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        creds.email,
                        creds.password,
                        ArrayList())
        )
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        val userName = (authResult?.principal as User).username
        val token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact()

        val userService: UserService = SpringApplicationContext.getBean("userServiceImpl") as UserService
        val userDto: UserDto = userService.getUser(userName)

        response?.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
        response?.addHeader("UserID", userDto.userId)
    }
}