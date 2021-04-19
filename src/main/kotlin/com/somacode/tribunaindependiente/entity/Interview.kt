package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class Interview(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Lob var body: String? = null,
        var videoUrl: String? = null
)