import com.google.gson.Gson
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.SelfUser
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class BotBehavior : ListenerAdapter(){
    private val prefix = config.prefix
    private val logger = Logger("ListenerAdapter")
    private val gson = Gson()
    private lateinit var channels : MutableMap<String,Double>

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
            if(command.toLowerCase() == "ping") event.channel.sendMessage("pong").queue()
            if(command.toLowerCase() == "setresponserate" || command.toLowerCase() == "srr"){
                val value : Double
                try{
                    value = content.toDouble()
                }catch (e : java.lang.Exception){
                    event.channel.sendMessage("Wrong argument type").queue()
                    return
                }
                if(value>1 || value<0){
                    event.channel.sendMessage("Value must be between 0 and 1").queue()
                    return
                }
                if(event.author.id != config.admin){
                    event.channel.sendMessage("Permission denied").queue()
                    return
                }
                channels[event.channel.id] = value
                event.channel.sendMessage("Set response rate to $value").queue()
                saveChannels()
                return
            }
        }

        //Check if message contains bot ping
        if(event.message.getMentions().contains(jda.selfUser)){
            //Reply in the same channel a random message
            event.channel.sendMessage(generateMarkov()).queue()
        }

        //Else check channel response rate and roll
        if(channels.containsKey(event.channel.id)){
            if(Math.random()<= channels[event.channel.id]!!) event.channel.sendMessage(generateMarkov()).queue()
        }
    }

    fun loadChannels(){
        try{
            channels = gson.fromJson(FileReader(config.channels), mutableMapOf<String,Double>().javaClass)
        }catch (e : Exception){
            if(File(config.channels).length() <= 1.toLong()) channels = mutableMapOf()
            else e.printStackTrace()
        }
    }

    private fun saveChannels(){
        gson.toJson(channels, FileWriter(config.channels))
    }
}
