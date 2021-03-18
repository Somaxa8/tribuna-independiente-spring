package com.somacode.tribunaindependiente.repository.criteria

import com.somacode.tribunaindependiente.entity.News
import com.somacode.tribunaindependiente.entity.NewsLabel_
import com.somacode.tribunaindependiente.entity.News_
import com.somacode.tribunaindependiente.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class NewsCriteria {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?, labelId: Long?): Page<News> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(News::class.java)
        val news = q.from(News::class.java)

        val n: Path<Set<String>> = news.get(News_.LABEL)
        val label: Path<Set<String>> = n.get(NewsLabel_.ID)
        val order: Path<Set<String>> = news.get(News_.ID)

        labelId?.let {
            q.select(news).where(cb.equal(label, labelId)).orderBy(cb.desc(order))
        } ?: run {
            q.select(news).where().orderBy(cb.desc(order))
        }

        return CriteriaTool.page(entityManager, q, page, size)
    }
}