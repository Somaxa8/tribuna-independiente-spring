package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class Interview(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Lob var body: String? = null,
        @OneToOne
        var image: Document? = null,
        var videoUrl: String? = null
): Auditing()