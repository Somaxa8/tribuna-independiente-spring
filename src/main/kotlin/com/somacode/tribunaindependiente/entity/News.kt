package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class News(
        @Id @GeneratedValue
        var id: Long? = null,
        @OneToOne
        var image: Document? = null,
        var title: String? = null,
        @Lob
        var body: String? = null,
        var featured: Boolean? = null,
        @ManyToOne
        var label: NewsLabel? = null
): Auditing()