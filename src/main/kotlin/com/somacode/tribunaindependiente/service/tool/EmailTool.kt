package com.somacode.tribunaindependiente.service.tool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import java.io.File
import java.util.regex.Pattern

//@Component
class EmailTool {

//    @Autowired lateinit var javaMailSender: JavaMailSender
    @Autowired lateinit var resourceLoader: ResourceLoader
    @Autowired lateinit var templateEngine: TemplateEngine
    @Value("\${spring.mail.username}") lateinit var from: String
    @Value("\${custom.email-enabled}") var emailEnabled: Boolean = false

    companion object {
        private val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        private const val BASE = "email/"

        fun validate(emailStr: String?): Boolean {
            if (emailStr == null) return false
            val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
            return matcher.find()
        }
    }

    enum class Type {
        REGISTER, ACTIVATE, DEACTIVATE, PENALIZE, RESERVATION, CANCELLATION, WAITING, PASSWORD
    }

    @Async
    fun send(email: String, subject: String, body: String) {
        send(email, subject, null, body, null, null)
    }

    @Async
    fun send(email: String, subject: String, body: String, file: File) {
        send(email, subject, file, body, null, null)
    }

    @Async
    fun send(email: String, subject: String, type: Type, params: Map<String, String>) {
        send(email, subject, null, null, type, params)
    }

    @Async
    fun send(email: String, subject: String, type: Type, params: Map<String, String>?, file: File?) {
        send(email, subject, file, null, type, params)
    }

    private fun send(email: String, subject: String, file: File?, body: String?, type: Type?, params: Map<String, String>?) {
//        val messagePreparator = MimeMessagePreparator { mimeMessage ->
//            val messageHelper = MimeMessageHelper(mimeMessage, file != null)
//            messageHelper.setFrom(from)
//            messageHelper.setTo(email)
//            messageHelper.setSubject(subject)
//            if (type == null) {
//                messageHelper.setText(body!!)
//            } else {
//                val context = Context()
//                for (key in params!!.keys) context.setVariable(key, params[key])
//                val process = templateEngine.process(BASE + type.toString().toLowerCase(), context)
//                messageHelper.setText(process, true)
//            }
//            if (file != null) {
//                messageHelper.addAttachment(file.name, file)
//            }
//        }
//        if (emailEnabled) {
//            javaMailSender.send(messagePreparator)
//        }
//        println("Sent email to: $email - $subject")
////        LogService.out.info("Email to: $email - $subject - $body")
    }


}