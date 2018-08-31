package it.giorgiopagnoni.springrestws.ui.response

enum class RequestOperationName {
    DELETE, VERIFY_EMAIL, REQUEST_PASSWORD_RESET
}

enum class RequestOperationStatus {
    ERROR, SUCCESS
}