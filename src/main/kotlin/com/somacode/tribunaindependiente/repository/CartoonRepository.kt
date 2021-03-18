package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Cartoon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartoonRepository : JpaRepository<Cartoon, Long> {
}