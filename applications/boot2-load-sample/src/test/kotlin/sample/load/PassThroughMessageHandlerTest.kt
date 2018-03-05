package sample.load

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters.fromObject

@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = arrayOf("loadtarget.host=http://localhost:7684"))
@AutoConfigureWebTestClient
class PassThroughMessageHandlerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private var wiremockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(7684));
    
    @BeforeEach
    fun before() {
        wiremockServer.start()
    }
    
    @AfterEach
    fun after() {
        wiremockServer.stop()
    }
    
    @Test
    @DisplayName("test a passthrough call")
    fun testPassThroughCall() {
        
        wiremockServer.stubFor(post(urlEqualTo("/messages"))
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
        
        wiremockServer.verify(postRequestedFor(urlMatching("/messages"))
                .withRequestBody(matching(".*one.*"))
                .withHeader("Content-Type", matching("application/json")))
    }
    
}