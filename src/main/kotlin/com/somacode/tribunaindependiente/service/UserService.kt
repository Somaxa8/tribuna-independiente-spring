package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.config.exception.BadRequestException
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Authority
import com.somacode.tribunaindependiente.entity.User
import com.somacode.tribunaindependiente.repository.UserRepository
import com.somacode.tribunaindependiente.service.tool.Constants
import com.somacode.tribunaindependiente.service.tool.EmailTool
import com.somacode.tribunaindependiente.service.tool.ProfileTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService {

    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var profileTool: ProfileTool
    @Autowired lateinit var oAuthService: OAuthService
    @Value("\${custom.username}") lateinit var username: String
    @Value("\${custom.password}") lateinit var password: String


    fun init() {
        if (userRepository.count() == 0L) {
            println("UserService init()")

            val user = User(
                    email = username,
                    password = passwordEncoder.encode(password),
                    name = "Administrator"
            )
            userRepository.save(user)
            authorityService.relateUser(Authority.Role.ADMIN, 1)
        }
    }

    fun register(email: String, password: String): User {
        if (!EmailTool.validate(email)) {
            throw BadRequestException("Email is not valid")
        }
        if (userRepository.existsByEmail(email)) {
            throw BadRequestException("Email already exists")
        }

        val user = User(
                email = email,
                password = passwordEncoder.encode(password)
        )

        userRepository.save(user)
        authorityService.relateUser(Authority.Role.ADMIN, user.id!!)
        return user
    }

    fun update(id: Long, request: User): User {
        val user = findById(id)
        request.name?.let { user.name = it }
        request.lastname?.let { user.lastname = it }
        request.phone?.let { user.phone = it }
        request.email?.let {
            oAuthService.logoutByUserId(id)
            user.email = it
        }

        return userRepository.save(user)
    }

    fun changePassword(id: Long, password: String, newPassword: String) {
        val user = findById(id)
        if (!passwordEncoder.matches(password, user.password)) {
            throw BadRequestException()
        }
        if (newPassword.isBlank() || newPassword.length < Constants.PASSWORD_MIN_SIZE) {
            throw BadRequestException()
        }
        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

    fun findById(id: Long): User {
        if (!userRepository.existsById(id)) {
            throw NotFoundException()
        }
        return userRepository.getOne(id)
    }

    fun findByEmail(email: String): User {
        if (!userRepository.existsByEmail(email)) {
            throw NotFoundException()
        }
        return userRepository.findByEmail(email)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun delete(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NotFoundException()
        }
        userRepository.deleteById(id)
    }

}