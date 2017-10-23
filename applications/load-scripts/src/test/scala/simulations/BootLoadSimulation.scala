package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BootLoadSimulation extends Simulation {

  val baseUrl = System.getProperty("TARGET_URL")
  val sim_users = System.getProperty("SIM_USERS").toInt

  val httpConf = http.baseURL(baseUrl)

  val headers = Map("Accept" -> """application/json""")

  val passThroughPage = repeat(30) {
    exec(http("passthrough-messages")
      .post("/passthrough/messages")
        .header("Content-Type", "application/json" )
      .body(StringBody(
        s"""
           | {
           |   "id": "${UUID.randomUUID().toString}",
           |   "payload": "test payload",
           |   "delay": 300
           | }
        """.stripMargin)))
      .pause(1 second, 2 seconds)
  }

  val scn = scenario("Passthrough Page")
    .exec(passThroughPage)

  setUp(scn.inject(rampUsers(sim_users).over(30 seconds)).protocols(httpConf))
}
