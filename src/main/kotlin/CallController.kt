package uk.co.placona.TwilioKotlin


import com.twilio.http.TwilioRestClient
import com.twilio.rest.api.v2010.account.Call
import com.twilio.type.PhoneNumber

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.twilio.twiml.Say
import com.twilio.twiml.VoiceResponse
import java.net.URI
import java.util.*


@RestController
public class CallController {
    val accountSid = System.getenv("TWILIO_ACCOUNT_SID")!!
    val authToken = System.getenv("TWILIO_AUTH_TOKEN")!!

    @RequestMapping(value = "/call")
    fun helloSpringBoot() = "Hello Spring Boot - Call"

    @RequestMapping(value = "/call/makeCall")
    fun makeCall(){

        val client = TwilioRestClient.Builder(accountSid, authToken).build()

        val call = Call.creator(
                PhoneNumber(System.getenv("MY_NUMBER")),
                PhoneNumber(System.getenv("TWILIO_NUMBER")),
                URI.create("http://69986211.ngrok.io/call/handleCall")).create(client)

        println(call.sid)
    }

    @RequestMapping(value = "/call/handleCall" , produces = arrayOf("text/xml"))
    fun handleCall(): String? {
        val time = Calendar.HOUR_OF_DAY;

        val greeting = "Good " + when {
            time < 12 -> "Morning"
            time < 18 -> "Afternoon"
            else -> "Night"
        }

        val voiceResponse = VoiceResponse.Builder()
                .say(Say.Builder(greeting).build())
                .build()

        return voiceResponse.toXml()
    }
}