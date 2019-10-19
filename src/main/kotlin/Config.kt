import com.google.gson.Gson
import java.io.File

class Config(val token : String, val prefix : Char, val enableCommandLog : Boolean, val data : String,
             val averageLength : Int, val channels : String, val admin : String)

fun loadConfig() : Config{
    val gson = Gson()
    return gson.fromJson(File("config.json").readText(),Config::class.java)
}