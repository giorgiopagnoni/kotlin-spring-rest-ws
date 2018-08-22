package it.giorgiopagnoni.springrestws

import it.giorgiopagnoni.springrestws.security.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class SpringRestWsApplication {
    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun springApplicationContext() = SpringApplicationContext()

    @Bean
    fun appProperties() = AppProperties()
}

fun main(args: Array<String>) {
    runApplication<SpringRestWsApplication>(*args)
}
