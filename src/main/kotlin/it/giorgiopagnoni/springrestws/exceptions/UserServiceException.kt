package it.giorgiopagnoni.springrestws.exceptions

class UserServiceException(override val message: String) : RuntimeException(message)