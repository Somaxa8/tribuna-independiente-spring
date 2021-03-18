package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.entity.Interview_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class InterviewCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Interview> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Interview::class.java)
        val interview = q.from(Interview::class.java)

        val order: Path<Set<String>> = interview.get(Interview_.ID)

        q.select(interview).where().orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}