package com.somacode.tribunaindependiente.config.exception

class ConflictException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(): super("Conflict")
}