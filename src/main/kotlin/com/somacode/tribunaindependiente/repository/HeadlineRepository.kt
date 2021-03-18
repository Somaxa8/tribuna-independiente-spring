package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Headline
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HeadlineRepository : JpaRepository<Headline, Long> {
}