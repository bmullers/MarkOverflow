import net.dv8tion.jda.core.JDABuilder
val config = loadConfig()

fun main(){
    var builder : JDABuilder = JDABuilder()
    builder.setToken(config.token)
    builder.addEventListener(BotBehavior())
    builder.build()
}