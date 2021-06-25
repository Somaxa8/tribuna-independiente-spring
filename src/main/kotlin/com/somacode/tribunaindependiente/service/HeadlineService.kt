package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.Headline
import com.somacode.tribunaindependiente.repository.HeadlineRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Cartoon
import com.somacode.tribunaindependiente.repository.criteria.HeadlineCriteria
import com.somacode.tribunaindependiente.service.tool.FakerTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class HeadlineService {

    @Autowired lateinit var headlineRepository: HeadlineRepository
    @Autowired lateinit var headlineCriteria: HeadlineCriteria
    @Value("\${custom.mock}") var mock: Boolean = false

    fun init() {
        if (mock) {
            println("HeadlineService init()")
            val faker = FakerTool.faker
            for (i in 1..20) {
                create(faker.lorem().paragraph(), "10:30 AM")
            }
        }
    }

    fun create(body: String, hour: String): Headline {
        if (body.isNullOrBlank() && hour.isNullOrBlank()) {
            throw IllegalArgumentException()
        }

        val headline = Headline(
                body = body,
                hour = hour
        )

        return headlineRepository.save(headline)
    }

    fun update(id: Long, body: String?, hour: String?): Headline {
        val headline = findById(id)
        body?.let { headline.body = it }
        hour?.let { headline.hour = it }

        return headlineRepository.save(headline)
    }

    fun findById(id: Long): Headline {
        if (!headlineRepository.existsById(id)) {
            throw NotFoundException()
        }
        return headlineRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!headlineRepository.existsById(id)) {
            throw NotFoundException()
        }
        headlineRepository.deleteById(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Headline> {
        return headlineCriteria.findFilterPageable(page, size, search)
    }
}