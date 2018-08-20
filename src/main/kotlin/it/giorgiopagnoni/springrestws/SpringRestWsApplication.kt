package it.giorgiopagnoni.springrestws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class SpringRestWsApplication {
    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}

fun main(args: Array<String>) {
    runApplication<SpringRestWsApplication>(*args)
}
