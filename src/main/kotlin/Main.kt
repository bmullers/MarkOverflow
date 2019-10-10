import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import stackoverflow.makeQuery
import stackoverflow.restClientInit
import java.util.*
import kotlin.concurrent.schedule

val config = loadConfig()
lateinit var jda : JDA

fun main(){
    var builder : JDABuilder = JDABuilder()
    builder.setToken(config.token)
    builder.addEventListener(BotBehavior())
    jda = builder.build()
    restClientInit()
    loadNgrams()
    val timer = Timer()
    //TODO : change from delay to fixed daily time
    timer.schedule(0,86400000){
        makeQuery(100,"stackoverflow")
        makeQuery(50,"superuser")
        makeQuery(50,"askubuntu")
        makeQuery(50,"serverfault")
        println("Saving ngrams")
        saveNgrams()
    }//This means 4 requests a day, ~120 requests a month and 250 new messages a day
}