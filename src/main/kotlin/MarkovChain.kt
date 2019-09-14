import java.io.*
import java.lang.Exception
import kotlin.math.log
import kotlin.random.Random
import kotlin.text.Regex.Companion.escape

const val order = 6 //This is an arbitrary value for the order of the n-grams
//var ngrams : MutableMap< String,MutableList<Char?>> = mutableMapOf() //the list of ngrams and their possible next characters
private val logger = Logger("Markov Chain text generator")

//This function loads the values gathered from an input text into a map of n-grams and next characters
fun loadMarkov(input : String){
    var ngrams : MutableMap< String,MutableList<Char?>> = mutableMapOf()
    try{
        var ngrams = ObjectInputStream(FileInputStream(config.data)).readObject() as MutableMap<String,MutableList<Char?>>
    }catch(e :EOFException){

    }
    //If the input is too small to fit on n-gram, add the  input and set the next character to null
    if(input.length < order) ngrams[input] = mutableListOf<Char?>(null)
    //Go through the whole input text except the last few characters to make sure all n-grams are properly sized
    for(i in 0 until (input.length-order-1)){
        //get the n-gram
        var gram = input.substring(i,i+order)
        if(!ngrams.containsKey(gram)){
            ngrams[gram] = mutableListOf<Char?>()
        }
        //add the next character
        val nextChar : Char?
        if(i+order<input.length-1)  nextChar = input[i+order]
        else nextChar = null
        ngrams[gram]?.add(nextChar) ?: throw Exception("BRUH MOMENT")
    }
    var gram = input.substring(input.length-order,input.length)
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
    var ngrams = ObjectInputStream(FileInputStream(config.data)).readObject() as MutableMap<String,MutableList<Char?>>

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
            logger.debug("Next character is a space")
            if(messageEnd(output.length)) return output.drop(0)
        }
        output += nextChar
    }
    return output.drop(0)
}