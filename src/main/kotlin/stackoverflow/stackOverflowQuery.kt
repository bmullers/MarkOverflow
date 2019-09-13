package stackoverflow

import Logger
import com.google.gson.Gson
import loadMarkov
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.zip.GZIPInputStream
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType


lateinit var client : Client

val LOG = Logger("StackOverflow Queries")
val gson = Gson()


fun restClientInit(){
    client = ClientBuilder.newClient()
}

fun makeQuery(n : Int, site : String){
    val dateNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    val dateBefore = LocalDateTime.now().minusHours(24).toEpochSecond(ZoneOffset.UTC)
    val target = client.target("https://api.stackexchange.com/2.2/answers") //GET answers
        .queryParam("pagesize",n) //Page size of 10
        .queryParam("page",1)
        .queryParam("fromdate",dateBefore) //From 2 hours ago
        .queryParam("todate",dateNow) //Until now
        .queryParam("order","desc") //Descending
        .queryParam("sort","votes") //On amount of votes
        .queryParam("site",site) //From StackOverflow
        .queryParam("filter","!9Z(-wzftf")
    val builder = target.request(MediaType.TEXT_PLAIN_TYPE).header("Accept-Encoding","GZIP");
    var response = builder.get()
    val gzipInputStream = GZIPInputStream(ByteArrayInputStream(response.readEntity(ByteArray::class.java)))
    val reader = InputStreamReader(gzipInputStream)
    val bufferedReader = BufferedReader(reader)
    var inflated = ""
    var read = bufferedReader.readLine()
    //print(response)
    while(read != null){
        inflated += read
        read = bufferedReader.readLine()
    }
    if(inflated != ""){
        val responseBody = gson.fromJson(inflated,Response::class.java)
        println(responseBody.items?.size)
        responseBody.items?.forEach { if(it.bodyMarkdown != null) loadMarkov(it.bodyMarkdown) }
    }
    else LOG.error("Stack App API request returned nothing")
}
