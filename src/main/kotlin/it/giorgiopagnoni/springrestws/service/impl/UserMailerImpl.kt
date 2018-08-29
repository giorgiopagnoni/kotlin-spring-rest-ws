package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.service.UserMailer
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class UserMailerImpl : UserMailer {

    val textBody = "http://localhost:8080/spring-rest-ws/users/email-verification?token=\$tokenValue"

    @Autowired
    lateinit var mailer: JavaMailSender

    override fun sendActivationEmail(user: UserDto) {
        val message = SimpleMailMessage()

        message.setTo(user.email)
        message.setSubject("Email Verification")
        message.setText(
                textBody.replace("\$tokenValue", user.emailVerificationToken.orEmpty())
        )

        mailer.send(message)
    }
}