package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.Blog
import com.somacode.tribunaindependiente.entity.Blog_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class BlogCriteria {

    @PersistenceContext lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Blog> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Blog::class.java)
        val blog = q.from(Blog::class.java)

        val order: Path<Set<String>> = blog.get(Blog_.ID)

        q.select(blog).where().orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}