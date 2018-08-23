package it.giorgiopagnoni.springrestws.ui.response

import java.util.*

class ErrorMessage(
        val message: String,
        val timestamp: Date = Date()
)