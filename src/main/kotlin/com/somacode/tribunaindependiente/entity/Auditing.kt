package com.somacode.tribunaindependiente.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditing(

        @CreatedDate
        @Column(updatable = false)
        var createdAt: LocalDate? = null,
        @LastModifiedDate
        var updatedAt: LocalDate? = null

): Serializable