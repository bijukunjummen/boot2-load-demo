package sample.load

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters.fromObject

@RunWith(SpringRunner::class)
@SpringBootTest(properties = arrayOf("loadtarget.host=http://localhost:7684"))
@AutoConfigureWebTestClient
class PassThroughMessageHandlerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Rule
    @JvmField
    final val wireMockRule = WireMockRule(7684)

    @Test
    fun testPassThroughCall() {
        stubFor(post(urlEqualTo("/messages"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(""" 
                         | {
                         |   "id": "1",
                         |   "received": "one",
                         |   "ack": "ack"
                         | }
                        """.trimMargin())))
        
        webTestClient.post()
                .uri("/passthrough/messages")
                .body(fromObject(Message("1", "one", 0)))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(""" 
                    | {
                    |   "id": "1",
                    |   "received": "one",
                    |   "ack": "ack"
                    | }
                """.trimMargin())
        
        verify(postRequestedFor(urlMatching("/messages"))
                .withRequestBody(matching(".*one.*"))
                .withHeader("Content-Type", matching("application/json")))
    }
    
}