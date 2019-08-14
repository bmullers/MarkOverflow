import net.dv8tion.jda.core.JDABuilder
import stackoverflow.makeQuery
import stackoverflow.restClientInit
import java.util.*
import kotlin.concurrent.schedule

val config = loadConfig()

fun main(){
    var builder : JDABuilder = JDABuilder()
    builder.setToken(config.token)
    builder.addEventListener(BotBehavior())
    builder.build()
    restClientInit()
    val timer = Timer()
    timer.schedule(0,7200000){ makeQuery()}
    //loadMarkov(ea)
    //loadMarkov(stuart)
    //loadMarkov("frog frog frog")
}