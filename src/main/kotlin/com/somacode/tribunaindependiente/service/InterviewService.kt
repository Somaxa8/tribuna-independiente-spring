package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.repository.InterviewRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Blog
import com.somacode.tribunaindependiente.repository.criteria.InterviewCriteria
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class InterviewService {

    @Autowired lateinit var interviewRepository: InterviewRepository
    @Autowired lateinit var interviewCriteria: InterviewCriteria


    fun init() {
        if (interviewRepository.count() <= 0) {
            create("\"LA GENERACIÓN CONTRA LA IMPUNIDAD\" por Fernando Calle", "prueba", "https://www.facebook.com/tribunaindependiente.pe/videos/3578470795611900/")
        }
    }

    fun create(title: String, body: String, videoUrl: String): Interview {
        if (title.isBlank() && body.isBlank() && videoUrl.isBlank()) {
            throw IllegalArgumentException()
        }
        val interview = Interview(
                title = title,
                body = body,
                videoUrl = videoUrl
        )

        return interviewRepository.save(interview)
    }

    fun update(id: Long, title: String?, body: String?, videoUrl: String?): Interview {
        val interview = findById(id)

        title?.let {
            if (title.isBlank()) throw IllegalArgumentException()
            interview.title = it
        }
        body?.let {
            if (body.isBlank()) throw IllegalArgumentException()
            interview.body = it
        }
        videoUrl?.let {
            if (videoUrl.isBlank()) throw IllegalArgumentException()
            interview.videoUrl = it
        }

        return interviewRepository.save(interview)
    }

    fun findById(id: Long): Interview {
        if (!interviewRepository.existsById(id)) {
            throw NotFoundException()
        }
        return interviewRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!interviewRepository.existsById(id)) {
            throw NotFoundException()
        }
        interviewRepository.deleteById(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Interview> {
        return interviewCriteria.findFilterPageable(page, size, search)
    }
}