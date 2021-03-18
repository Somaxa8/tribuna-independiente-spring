package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.NewsLabel
import com.somacode.tribunaindependiente.repository.NewsLabelRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class NewsLabelService {

    @Autowired lateinit var newsLabelRepository: NewsLabelRepository


    fun init() {
        if (newsLabelRepository.count() <= 0) {
            create("Política")
        }
    }

    fun create(title: String): NewsLabel {
        if (title.isBlank()) throw IllegalArgumentException()

        val newsLabel = NewsLabel(title = title)

        return newsLabelRepository.save(newsLabel)
    }

    fun findById(id: Long): NewsLabel {
        if (!newsLabelRepository.existsById(id)) {
            throw NotFoundException()
        }
        return newsLabelRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!newsLabelRepository.existsById(id)) {
            throw NotFoundException()
        }
        newsLabelRepository.deleteById(id)
    }

    fun findAll(): List<NewsLabel> {
        return newsLabelRepository.findAll()
    }
}