package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): User

    fun existsByEmail(email: String): Boolean

}