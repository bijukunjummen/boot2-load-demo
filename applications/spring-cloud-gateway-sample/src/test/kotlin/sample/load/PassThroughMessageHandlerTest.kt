package sample.load

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import java.time.Duration

@RunWith(SpringRunner::class)
@SpringBootTest(properties = arrayOf("loadtarget.host=http://localhost:7684"),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PassThroughMessageHandlerTest {


    @Rule
    @JvmField
    final val wireMockRule = WireMockRule(7684)


    @LocalServerPort
    private var port: Int = 0

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

        val webClient = WebClient.create("http://localhost:${port}")

        val clientResponse = webClient.post()
                .uri("/passthrough/messages")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(BodyInserters.fromObject(""" 
                         | {
                         |   "id": "1",
                         |   "payload": "one",
                         |   "delay": 100
                         | }
                        """.trimMargin()))
                .exchange()

        StepVerifier.create<ClientResponse>(clientResponse)
                .consumeNextWith { response -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK) }
                .expectComplete()
                .verify(Duration.ofSeconds(3))

        verify(postRequestedFor(urlMatching("/messages"))
                .withRequestBody(matching(".*one.*"))
                .withHeader("Content-Type", matching("application/json")))
    }

}