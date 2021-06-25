package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Blog
import com.somacode.tribunaindependiente.entity.Document
import com.somacode.tribunaindependiente.entity.Slider
import com.somacode.tribunaindependiente.repository.BlogRepository
import com.somacode.tribunaindependiente.repository.criteria.BlogCriteria
import com.somacode.tribunaindependiente.service.tool.FakerTool
import com.somacode.tribunaindependiente.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class BlogService {

    @Autowired lateinit var blogRepository: BlogRepository
    @Autowired lateinit var blogCriteria: BlogCriteria
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool
    @Value("\${custom.mock}") var mock: Boolean = false

    fun init() {
        if (mock) {
            println("BlogService init()")
            val faker = FakerTool.faker
            for (i in 1..20) {
                create(faker.lorem().characters(10, 18), faker.lorem().paragraph(10), mockTool.multipartFileImage())
            }
        }
    }

    fun create(title: String, body: String, imageFile: MultipartFile): Blog {
        if (title.isBlank() && body.isBlank()) {
            throw IllegalArgumentException()
        }

        val blog = Blog(
                title = title,
                body = body,
                image = documentService.create(imageFile, Document.Type.IMAGE, Blog::class.java.simpleName, null)
        )

        return blogRepository.save(blog)
    }

    fun update(id: Long, title: String?, body: String?, imageFile: MultipartFile?): Blog {
        val blog = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.body = it
        }
        if (imageFile != null) {
            val oldImage = blog.image
            blog.image = documentService.create(imageFile, Document.Type.IMAGE, Blog::class.java.simpleName, null)
            oldImage?.let { documentService.delete(it.id!!) }
        }

        return blogRepository.save(blog)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Blog> {
        return blogCriteria.findFilterPageable(page, size, search)
    }

    fun findById(id: Long): Blog {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        return blogRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        val blog = findById(id)
        blog.image?.let { documentService.delete(it.id!!) }
        blogRepository.deleteById(id)
    }
}