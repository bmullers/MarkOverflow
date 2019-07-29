import java.lang.Exception
import kotlin.math.log
import kotlin.text.Regex.Companion.escape

//For now, I use fixed input. This is an answer I took from stackoverflow.
val input = "What the fuck did you just fucking say about me, you little bitch? I'll have you know I graduated top of" +
        " my class in the Navy Seals, and I've been involved in numerous secret raids on Al-Quaeda, and I have over 300" +
        " confirmed kills. I am trained in gorilla warfare and I'm the top sniper in the entire US armed forces. " +
        "You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which" +
        " has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that" +
        " shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies " +
        "across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm" +
        " that wipes out the pathetic little thing you call your life. You're fucking dead, kid. I can be anywhere," +
        " anytime, and I can kill you in over seven hundred ways, and that's just with my bare hands. Not only am I " +
        "extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine" +
        " Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you " +
        "little shit. If only you could have known what unholy retribution your little \"clever\" comment was about" +
        " to bring down upon you, maybe you would have held your fucking tongue. But you couldn't, you didn't, and" +
        " now you're paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it." +
        " You're fucking dead, kiddo."

val order = 6 //This is an arbitrary value for the order of the n-grams
var ngrams : MutableMap< String,ArrayList<Char>> = mutableMapOf() //the list of ngrams and their possible next characters
val logger = Logger("Markov Chain text generator")

//This function loads the values gathered from an input text into a map of n-grams and next characters
fun loadMarkov(){
    //Go through the whole input text except the last few characters to make sure all n-grams are properly sized
    for(i in 0 until (input.length-order)){
        //get the n-gram
        var gram = input.substring(i,i+order)
        if(!ngrams.containsKey(gram)){
            ngrams[gram] = ArrayList<Char>()
        }
        //add the next character
        ngrams[gram]?.add(input[i+order]) ?: throw Exception("BRUH MOMENT")
    }
}

//This function takes a map of n-grams and next characters and randomly generates text
fun generateMarkov() : String{
    //start at a random n-gram
    var currentGram = ngrams.keys.random()
    var output = currentGram
    //add characters until character limit is reached
    for(i in 0..250){
        currentGram = output.substring(i,i+order)
        logger.info("new n-gram : $currentGram")
        //take the ngram and check if it has next characters
        val nextChars = ngrams[currentGram]
        if(nextChars.isNullOrEmpty()) {
            return output}
        //add random character to output and select next n-gram
        output += nextChars.random()
    }
    return output
}