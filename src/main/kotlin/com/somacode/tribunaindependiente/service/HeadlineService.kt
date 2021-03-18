package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.Headline
import com.somacode.tribunaindependiente.repository.HeadlineRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class HeadlineService {

    @Autowired lateinit var headlineRepository: HeadlineRepository


    fun init() {
        if (headlineRepository.count() <= 0) {
            create("lorem ipsum", "12:00 AM")
        }
    }

    fun create(body: String, hour: String): Headline {
        if (body.isBlank() && hour.isBlank()) {
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

    fun findAll(): List<Headline> {
        return headlineRepository.findAll()
    }
}