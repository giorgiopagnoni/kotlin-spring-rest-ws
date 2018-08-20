package it.giorgiopagnoni.springrestws.security

import it.giorgiopagnoni.springrestws.service.UserService
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class WebSecurity(
        val userDetailsService: UserService,
        val bCryptPasswordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()?.authorizeRequests()
                ?.antMatchers(HttpMethod.POST, "/users")
                ?.permitAll()
                ?.anyRequest()?.authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)
                ?.passwordEncoder(bCryptPasswordEncoder)
    }
}