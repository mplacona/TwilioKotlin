package uk.co.placona.TwilioKotlin


import com.twilio.http.TwilioRestClient

import com.twilio.rest.api.v2010.account.MessageCreator
import com.twilio.type.PhoneNumber
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.twilio.twiml.Body
import com.twilio.twiml.Message
import com.twilio.twiml.MessagingResponse

@RestController
public class SMSController {
    val accountSid = System.getenv("TWILIO_ACCOUNT_SID")!!
    val authToken = System.getenv("TWILIO_AUTH_TOKEN")!!

    @RequestMapping(value = "/")
    fun helloSpringBoot() = "Hello Spring Boot"

    @RequestMapping(value = "/sms/sendMessage")
    fun sendMessage(){

        val client = TwilioRestClient.Builder(accountSid, authToken).build()

        val message = MessageCreator(
                PhoneNumber(System.getenv("MY_NUMBER")),
                PhoneNumber(System.getenv("TWILIO_NUMBER")),
                "Look ma I’m type inferred!").create(client)

        println(message.sid)
    }

    @RequestMapping(value = "/sms/replyMessage", produces = arrayOf("text/xml"))
    fun replyMessage(): String? {
        val message = Message.Builder().body(Body("Be getting back to you soon, let me do some more Kotlin first")).build();
        return MessagingResponse.Builder().message(message).build().toXml();
    }
}