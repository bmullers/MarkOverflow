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
    timer.schedule(0,86400000){
        makeQuery(100,"stackoverflow")
        makeQuery(50,"superuser")
        makeQuery(50,"askubuntu")
        makeQuery(50,"serverfault")
    }//This means 4 requests a day, ~120 requests a month and 250 new messages a day
}