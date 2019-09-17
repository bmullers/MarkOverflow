import org.jsoup.parser.Parser.unescapeEntities
import java.io.*
import kotlin.random.Random

const val order = 7 //This is an arbitrary value for the order of the n-grams
//the list of ngrams and their possible next characters
lateinit var ngrams : MutableMap< String,MutableList<Char?>>

private val logger = Logger("Markov Chain text generator")

fun loadNgrams(){
    try{
        ngrams = ObjectInputStream(FileInputStream(config.data)).readObject() as MutableMap<String,MutableList<Char?>>
    }catch (e : EOFException){
        if(File(config.data).length() <= 1.toLong()) ngrams = mutableMapOf()
        else e.printStackTrace()
    }
}

//This function loads the values gathered from an input text into a map of n-grams and next characters
fun loadMarkov(input : String){
    //Unescape html special characters in the input
    val unescapedInput = unescapeEntities(input,true)
    //If the input is too small to fit on n-gram, add the  input and set the next character to null
    if(unescapedInput.length < order) ngrams[unescapedInput] = mutableListOf<Char?>(null)
    //Go through the whole input text except the last few characters to make sure all n-grams are properly sized
    for(i in 0 until (unescapedInput.length-order-1)){
        //get the n-gram
        var gram = unescapedInput.substring(i,i+order)
        if(!ngrams.containsKey(gram)){
            ngrams[gram] = mutableListOf<Char?>()
        }
        //add the next character
        val nextChar : Char?
        if(i+order<unescapedInput.length-1)  nextChar = unescapedInput[i+order]
        else nextChar = null
        ngrams[gram]?.add(nextChar) ?: throw Exception("BRUH MOMENT")
    }
    var gram = input.substring(unescapedInput.length-order,input.length)
    if(!ngrams.containsKey(gram)){
        ngrams[gram] = mutableListOf<Char?>()
    }
    ngrams[gram]?.add(null) ?: throw Exception("BRUH MOMENTO")
    ObjectOutputStream(FileOutputStream(config.data)).writeObject(ngrams)
}

//This function takes a map of n-grams and next characters and randomly generates text
fun generateMarkov() : String{
    //start at a random n-gram beginning with a space, thus ensuring a word's beginning
    //since words sent at the beginning of the messages can't have a space behind them
    //append a list of message-beginning n-grams
    val roll = Random.nextDouble()

    var currentGram = ngrams.keys.filter{k-> k[0] == ' '}.random(Random.Default)
    var output = currentGram
    //add characters until character limit is reached
    for(i in 0..(2000-order)){
        //select the next n-gram
        currentGram = output.substring(i,i+order)
        //take the ngram and check if it has next characters
        val nextChars = ngrams[currentGram]
        if(nextChars.isNullOrEmpty()) {
            return output.drop(0)}
        //add random character to output
        val nextChar = nextChars.random()?:return output.drop(0)
        //if at the end of a word, roll to see if the message should end
        if(nextChar == ' '){
            if(messageEnd(output.length,roll)) return output.drop(0)
        }
        output += nextChar
    }
    return output.drop(0)
}