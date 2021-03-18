package com.somacode.tribunaindependiente.config.exception

class NotFoundException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(): super("NotFound")
}