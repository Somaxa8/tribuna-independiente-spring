package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.Cartoon
import com.somacode.tribunaindependiente.entity.Cartoon_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class CartoonCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Cartoon> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Cartoon::class.java)
        val cartoon = q.from(Cartoon::class.java)

        val order: Path<Set<String>> = cartoon.get(Cartoon_.ID)

        q.select(cartoon).where().orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}