import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.SelfUser
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class BotBehavior : ListenerAdapter(){
    private val prefix = config.prefix
    private val logger = Logger("ListenerAdapter")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        //If it's send from a bot nothing happens
        if(event.author.isBot) return

        var message = event.message.contentRaw?:""

        //Check for prefix, if prefix check for command
        if(message != "" && message[0] == prefix){
            //drop prefix from message
            message = message.drop(1)
            //retrieve command
            val command = message.split(" ")[0]
            val content = message.drop(command.length+1)
            if(config.enableCommandLog) logger.info("Received command $command from user ${event.author.name}")
            //ADD COMMANDS HERE
            if(command == "ping") event.channel.sendMessage("pong").queue()

        }

        //Check if message contains bot ping
        if(event.message.getMentions().contains(jda.selfUser)){
            //Reply in the same channel a random message
            event.channel.sendMessage(generateMarkov()).queue()
        }

        //Else does nothing
        //TODO : keep a map of channels and probabilities of response - stored in file
    }
}