package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.Cartoon_
import com.somacode.tribunaindependiente.entity.Headline
import com.somacode.tribunaindependiente.entity.Headline_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class HeadlineCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Headline> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Headline::class.java)
        val headline = q.from(Headline::class.java)

        val order: Path<Set<String>> = headline.get(Headline_.ID)

        q.select(headline).where().orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}