import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import stackoverflow.makeQuery
import stackoverflow.restClientInit
import java.sql.Time
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.schedule

class Main {
    val config = loadConfig()
    lateinit var jda : JDA


    fun main(launchArguments: Array<String>){
        var builder : JDABuilder = JDABuilder()
        builder.setToken(config.token)
        val behavior = BotBehavior()
        builder.addEventListener(behavior)
        jda = builder.build()
        restClientInit()
        println("bruh")
        loadNgrams()
        behavior.loadChannels()
        val timer = Timer()
        timer.schedule(0,86400000){
            makeQuery(100,"stackoverflow")
            makeQuery(50,"superuser")
            makeQuery(50,"askubuntu")
            makeQuery(50,"serverfault")
            println("${LocalDateTime.now()} Saving ngrams")
            saveNgrams()
        }//This means 4 requests a day, ~120 requests a month and 250 new messages a day
    }
}

