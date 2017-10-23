package sample.load;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "loadtarget.host=http://localhost:8989")
@AutoConfigureMockMvc
public class PassThroughMessagesTest {

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8989);

    @Test
    public void testPassthroughCall() throws Exception {

        stubFor(WireMock.post(urlEqualTo("/messages"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"id\": \"1\",\n" +
                                "  \"received\": \"sample payload\",\n" +
                                "  \"ack\": \"ack\"\n" +
                                "}\n")));

        mockMvc.perform(
                post("/passthrough/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": \"1\",\n" +
                                "  \"payload\": \"sample payload\",\n" +
                                "  \"delay\": 0\n" +
                                "}\n"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{\n" +
                        "  \"id\": \"1\",\n" +
                        "  \"received\": \"sample payload\",\n" +
                        "  \"ack\": \"ack\"\n" +
                        "}"));

        verify(
                postRequestedFor(urlMatching("/messages"))
                        .withRequestBody(matching(".*sample.*"))
        );
    }
}
