package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.User
import com.somacode.tribunaindependiente.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Validated
@RestController
class UserController {

    @Autowired lateinit var userService: UserService


    @PostMapping("/api/users/register")
    fun postRegister(
            @RequestParam @Email email: String,
            @RequestParam @Size(min = 4) password: String,
            @RequestParam @Size(min = 4) fullName: String
    ): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.register(email, password, fullName)
        )
    }

    @PatchMapping("/api/users/{id}")
    fun patchUser(
            @PathVariable id: Long,
            @RequestBody user: User
    ): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, user))
    }

    @PatchMapping("/api/users/{id}/change-password")
    fun patchUserChangePassword(
            @PathVariable id: Long,
            @RequestParam password: String,
            @RequestParam newPassword: String
    ): ResponseEntity<Void> {
        userService.changePassword(id, password, newPassword)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/api/users")
    fun getUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll())
    }

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id))
    }

    @DeleteMapping("/api/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

}