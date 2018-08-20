package it.giorgiopagnoni.springrestws.security

object SecurityConstants {
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/users"
    const val TOKEN_SECRET = "8ahsdsadf9sdjksr"
}