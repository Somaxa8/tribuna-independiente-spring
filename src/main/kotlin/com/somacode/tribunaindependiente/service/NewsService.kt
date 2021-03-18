package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.News
import com.somacode.tribunaindependiente.repository.NewsRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Blog
import com.somacode.tribunaindependiente.entity.Document
import com.somacode.tribunaindependiente.repository.criteria.BlogCriteria
import com.somacode.tribunaindependiente.repository.criteria.NewsCriteria
import com.somacode.tribunaindependiente.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class NewsService {

    @Autowired lateinit var newsRepository: NewsRepository
    @Autowired lateinit var newsCriteria: NewsCriteria
    @Autowired lateinit var newsLabelService: NewsLabelService
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (newsRepository.count() <= 0) {
            create("Prueba1", "lorem", mockTool.multipartFileImage(), false, 1)
            create("Prueba2", "lorem", mockTool.multipartFileImage(), true, 1)
        }
    }

    fun create(title: String, body: String, imageFile: MultipartFile, featured: Boolean = false, labelId: Long?): News {
        if (title.isBlank() && body.isBlank()) {
            throw IllegalArgumentException()
        }

        val news = News(
                title = title,
                body = body,
                image = documentService.create(imageFile, Document.Type.IMAGE, News::class.java.simpleName, null),
                featured = featured
        )

        labelId?.let { news.label = newsLabelService.findById(it) }

        return newsRepository.save(news)
    }

    fun update(id: Long, title: String?, body: String?, imageFile: MultipartFile?, featured: Boolean = false, labelId: Long?): News {
        val news = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            news.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            news.body = it
        }
        labelId?.let { news.label = newsLabelService.findById(it) }
        if (imageFile != null) {
            val oldImage = news.image
            news.image = documentService.create(imageFile, Document.Type.IMAGE, News::class.java.simpleName, null)
            oldImage?.let { documentService.delete(it.id!!) }
        }
        news.featured = featured

        return newsRepository.save(news)
    }

    fun findById(id: Long): News {
        if (!newsRepository.existsById(id)) {
            throw NotFoundException()
        }
        return newsRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!newsRepository.existsById(id)) {
            throw NotFoundException()
        }
        val news = findById(id)
        news.image?.let { documentService.delete(it.id!!) }
        newsRepository.deleteById(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?, labelId: Long?): Page<News> {
        return newsCriteria.findFilterPageable(page, size, search, labelId)
    }
}