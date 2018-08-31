package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.service.UserMailer
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class UserMailerImpl : UserMailer {

    val activationEmailBody = "http://localhost:8080/spring-rest-ws/users/email-verification?token=\$tokenValue"
    val resetPasswordEmailBody = "http://localhost:8080/spring-rest-ws/users/password-reset-request?token=\$tokenValue"

    @Autowired
    lateinit var mailer: JavaMailSender

    override fun sendActivationEmail(user: UserDto) {
        val message = SimpleMailMessage()

        message.setTo(user.email)
        message.setSubject("Email Verification")
        message.setText(
                activationEmailBody.replace("\$tokenValue", user.emailVerificationToken.orEmpty())
        )

        mailer.send(message)
    }

    override fun sendPasswordResetEmail(email: String, token: String) {
        val message = SimpleMailMessage()

        message.setTo(email)
        message.setSubject("Password reset")
        message.setText(
                resetPasswordEmailBody.replace("\$tokenValue", token)
        )

        mailer.send(message)
    }
}