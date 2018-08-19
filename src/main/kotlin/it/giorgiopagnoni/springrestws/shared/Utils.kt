package it.giorgiopagnoni.springrestws.shared

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class Utils {
    private val random = SecureRandom()
    private val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    //const val ITERATIONS = 10000
    //const val KEY_LENGHT = 256

    fun generateUserId(length: Int): String = generateRandomString(length)

    private fun generateRandomString(length: Int): String {
        val returnValue = StringBuilder(length)
        for (i in 0..length) {
            returnValue.append(alphabet[random.nextInt(alphabet.length)])
        }
        return returnValue.toString()
    }
}