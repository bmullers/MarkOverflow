import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class BotBehavior : ListenerAdapter(){
    private val prefix = config.prefix
    private val logger = Logger("ListenerAdapter")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        //If it's send from a bot nothing happens either
        if(event.author.isBot) return

        var message = event.message.contentRaw?:""

        //Check for prefix, if no prefix then nothing happens (for now)
        if(message != "" && message[0] != prefix){
            return
        }

        //drop prefix from message
        message = message.drop(1)
        //retrieve command
        val command = message.split(" ")[0]
        val content = message.drop(command.length+1)
        if(config.enableCommandLog) logger.info("Received command $command from user ${event.author.name}")

        if(command == "ping") event.channel.sendMessage("pong").queue()
        if(command == "bruh") event.channel.sendMessage("bruh").queue()
        if(command == "boop") event.channel.sendMessage(event.author.asMention).queue()
        if(command == "gen") {
            var message = generateMarkov()
            if(message.length>2000) message = message.substring(0..1999)
            event.channel.sendMessage(message).queue()
        }
        if(command == "load") {
            if(!content.isNullOrBlank()){
                logger.info("Loading $content in the database")
                loadMarkov(content)
                event.channel.sendMessage("Successfully loaded message :clown:").queue()
            }else event.channel.sendMessage("My boy you better feed me some text or will stab you in the" +
                    " spleen :japanese_goblin:").queue()
        }
    }
}