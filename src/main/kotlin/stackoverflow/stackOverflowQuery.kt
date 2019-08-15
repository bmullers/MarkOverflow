package stackoverflow

import Logger
import com.google.gson.Gson
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
    val builder = target.request(MediaType.TEXT_PLAIN_TYPE).header("Accept-Encoding","GZIP");
    var response = builder.get()
    val gzipInputStream = GZIPInputStream(ByteArrayInputStream(response.readEntity(ByteArray::class.java)))
    val reader = InputStreamReader(gzipInputStream)
    val bufferedReader = BufferedReader(reader)
    var inflated = ""
    var read = bufferedReader.readLine()
    while(read != null){
        inflated += read
        println(read)
        read = bufferedReader.readLine()
    }
    if(inflated != ""){
        val responseBody = gson.fromJson(inflated,Response::class.java)
        println(responseBody)
    }
    else LOG.error("Stack App API request returned nothing")
}