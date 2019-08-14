package stackoverflow

import Logger

import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

lateinit var client : Client

val LOG = Logger("StackOverflow Queries")

fun restClientInit(){
    client = ClientBuilder.newClient()
    //TODO : OAuth here
}

fun makeQuery(){
    val dateNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    val dateBefore = LocalDateTime.now().minusHours(2).toEpochSecond(ZoneOffset.UTC)
    val target = client.target("https://api.stackexchange.com/2.2/answers") //GET answers
        .queryParam("pagesize",10) //Page size of 10
        .queryParam("fromdate",dateBefore) //From 2 hours ago
        .queryParam("todate",dateNow) //Until now
        .queryParam("order","desc") //Descending
        .queryParam("sort","votes") //On amount of votes
        .queryParam("site","stackoverflow") //From StackOverflow
    println(target.request(MediaType.APPLICATION_JSON).get())
}