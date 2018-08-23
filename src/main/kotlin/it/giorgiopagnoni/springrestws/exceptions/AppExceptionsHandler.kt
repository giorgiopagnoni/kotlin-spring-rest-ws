package it.giorgiopagnoni.springrestws.exceptions

import it.giorgiopagnoni.springrestws.ui.response.ErrorMessage
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class AppExceptionsHandler {

    @ExceptionHandler(value = [UserServiceException::class])
    fun handleUserServiceException(ex: UserServiceException, req: WebRequest): ResponseEntity<Any> {
        // do something useful here
        val errorMessage = ErrorMessage(ex.message)
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /*@ExceptionHandler(value = [Exception::class])
    fun handleOtherExceptions(ex: Exception, req: WebRequest): ResponseEntity<Any> {
        // do something useful here
        val errorMessage = ErrorMessage(ex.message?: "Opps")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }*/
}