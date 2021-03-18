package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.model.LoginResponse
import com.somacode.tribunaindependiente.service.OAuthService
import com.somacode.tribunaindependiente.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuthController {

    @Autowired lateinit var oAuthService: OAuthService
    @Autowired lateinit var userService: UserService


    @PostMapping("/public/oauth/login")
    fun postLogin(@RequestParam username: String, @RequestParam password: String): ResponseEntity<LoginResponse> {
        val user = userService.findByEmail(username)
        return ResponseEntity.status(HttpStatus.OK).body(
                LoginResponse(
                        oAuthService.login(username, password),
                        user,
                        user.authorities
                )
        )
    }

    @PostMapping("/api/oauth/logout")
    fun postLogout(): ResponseEntity<Void> {
        oAuthService.logout()
        return ResponseEntity.status(HttpStatus.OK).body(null)
    }

}