package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class Blog(
        @Id @GeneratedValue
        var id: Long? = null,
        @OneToOne
        var image: Document? = null,
        var title: String? = null,
        @Lob var body: String? = null
): Auditing()