package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Blog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogRepository : JpaRepository<Blog, Long> {
}