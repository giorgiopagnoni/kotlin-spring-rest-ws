package it.giorgiopagnoni.springrestws.shared

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import it.giorgiopagnoni.springrestws.security.SecurityConstants
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*

@Component
class Utils {
    private val random = SecureRandom()
    private val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    //const val ITERATIONS = 10000
    //const val KEY_LENGHT = 256

    fun generateUserId(length: Int): String = generateRandomString(length)

    fun generateAddressId(length: Int): String = generateRandomString(length)

    private fun generateRandomString(length: Int): String {
        val returnValue = StringBuilder(length)
        for (i in 0..length) {
            returnValue.append(alphabet[random.nextInt(alphabet.length)])
        }
        return returnValue.toString()
    }

    fun generateEmailVerificationToken(userId: String): String {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact()
    }

    fun generatePasswordResetToken(userId: String): String {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact()
    }

    fun hasTokenExpired(token: String): Boolean {
        val claims = Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token).body

        val tokenExpirationDate = claims.expiration
        val todayDate = Date()

        return tokenExpirationDate.before(todayDate)
    }
}