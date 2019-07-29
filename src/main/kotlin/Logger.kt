import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Logger(val name : String){
    fun info(text : String){
        println("${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)} : [INFO] - $name - $text")
    }
    fun warn(text : String){
        println("${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)} : [WARN] - $name - $text")
    }
    fun error(text : String){
        println("${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)} : [ERROR] - $name - $text")
    }
    fun debug(text : String){
        println("${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)} : [DEBUG] - $name - $text")
    }
}