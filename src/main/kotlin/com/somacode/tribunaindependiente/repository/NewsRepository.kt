package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.News
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository : JpaRepository<News, Long> {
}