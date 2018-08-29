package it.giorgiopagnoni.springrestws.service.impl

import it.giorgiopagnoni.springrestws.service.UserMailer
import it.giorgiopagnoni.springrestws.shared.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class UserMailerImpl : UserMailer {

    @Autowired
    lateinit var javaMailSender: JavaMailSender

    override fun sendActivationEmail(user: UserDto) {

    }
}