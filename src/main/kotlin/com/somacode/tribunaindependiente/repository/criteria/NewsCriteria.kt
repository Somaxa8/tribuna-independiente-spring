package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.News
import com.somacode.tribunaindependiente.entity.NewsLabel
import com.somacode.tribunaindependiente.entity.NewsLabel_
import com.somacode.tribunaindependiente.entity.News_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Join
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate

@Repository
class NewsCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?, labelId: Long?, featured: Boolean = false): Page<News> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(News::class.java)
        val news = q.from(News::class.java)
        val order: Path<Set<String>> = news.get(News_.ID)

        val predicates: MutableList<Predicate> = mutableListOf()
        val label: Join<News, NewsLabel> = news.join(News_.label)

        labelId?.let {
            predicates.add(cb.equal(label.get(NewsLabel_.id), labelId))
        }

        if (featured) {
            predicates.add(cb.equal(news.get(News_.featured), true))
        }

        q.select(news).where(
                *predicates.toTypedArray()
        ).orderBy(cb.desc(order))

        return CriteriaTool.page(entityManager, q, page, size)
    }
}