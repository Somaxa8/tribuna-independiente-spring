package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository: JpaRepository<Document, Long> {

}