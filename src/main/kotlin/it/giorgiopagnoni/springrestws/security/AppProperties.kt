package it.giorgiopagnoni.springrestws.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class AppProperties {

    @Autowired
    lateinit var env: Environment

    fun getTokenSecret(): String? = env.getProperty("tokenSecret")
}