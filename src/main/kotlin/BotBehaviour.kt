import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class BotBehavior : ListenerAdapter(){
    private val prefix = config.prefix
    private val logger = Logger("ListenerAdapter")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        //If it's send from a bot nothing happens either
        if(event.author.isBot) return

        var message = event.message.contentRaw

        //Check for prefix, if no prefix then nothing happens
        if(message[0] != prefix) return

        //drop prefix from message
        message = message.drop(1)
        if(config.enableCommandLog) logger.info("Received command $message from user ${event.author.name}")
        val messageBuilder = MessageBuilder()

        if(message == "ping") event.channel.sendMessage("pong").queue()
        if(message == "bruh") event.channel.sendMessage("bruh").queue()
        if(message == "boop") event.channel.sendMessage(event.author.asMention).queue()
    }
}