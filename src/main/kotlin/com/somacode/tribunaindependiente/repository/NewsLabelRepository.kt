package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.NewsLabel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsLabelRepository : JpaRepository<NewsLabel, Long> {
}