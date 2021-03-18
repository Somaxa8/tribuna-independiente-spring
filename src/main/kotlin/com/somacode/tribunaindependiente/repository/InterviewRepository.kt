package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Interview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InterviewRepository : JpaRepository<Interview, Long> {
}