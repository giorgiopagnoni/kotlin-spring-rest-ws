package it.giorgiopagnoni.springrestws.security

import it.giorgiopagnoni.springrestws.SpringApplicationContext

object SecurityConstants {
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/users"
    const val EMAIL_VERIFICATION_URL = "/users/email-verification"
    const val PASSWORD_RESET_VERIFICATION_URL = "/users/password-reset-request"

    fun getTokenSecret(): String? {
        val appProperties = SpringApplicationContext.getBean("appProperties") as AppProperties
        return appProperties.getTokenSecret()
    }
}