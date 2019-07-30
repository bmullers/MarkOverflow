import net.dv8tion.jda.core.JDABuilder
val config = loadConfig()

fun main(){
    var builder : JDABuilder = JDABuilder()
    builder.setToken(config.token)
    builder.addEventListener(BotBehavior())
    builder.build()
    //loadMarkov(ea)
    //loadMarkov(stuart)
    loadMarkov("Hello, I am a chatbot designed to say random bullshit lmaoooo :joy:")
}