package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class Cartoon(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Lob var body: String? = null,
        @OneToOne
        var image: Document? = null
): Auditing()