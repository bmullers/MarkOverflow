import java.lang.Exception
import kotlin.math.log
import kotlin.text.Regex.Companion.escape

//For now, I use fixed input.
val ea = "The intent is to provide players with a sense of pride and accomplishment for unlocking different heroes." +
        " As for cost, we selected initial values based upon data from the Open Beta and other adjustments made to" +
        " milestone rewards before launch. Among other things, we're looking at average per-player credit earn rates " +
        "on a daily basis, and we'll be making constant adjustments to ensure that players have challenges that are" +
        " compelling, rewarding, and of course attainable via gameplay. We appreciate the candid feedback, and the" +
        " passion the community has put forth around the current topics here on Reddit, our forums and across numerous" +
        " social media outlets. Our team will continue to make changes and monitor community feedback and update" +
        " everyone as soon and as often as we can."

val stuart = "I fucking hate Stuart Little. I know what you’re thinking, this is some kind of funny joke, but no." +
        " Stuart Little is a piece of shit. A damn rat got picked over actual children at an orphanage and he’s " +
        "supposed to be a hero? And I can’t even tell you how many damn times I’ve seen a great parking space only" +
        " to turn the corner and realise Stuart Little is already parked there in his stupid little fucking " +
        "convertible. He took my wife and the kids and my house and my job. I swear to fucking god, I’m going to" +
        " kill myself and take that goddamn rodent to hell with me. Stuart Little has ruined my family. Last summer," +
        " I approached the miserable mouse in the street, and asked him for his autograph, because my son is a huge" +
        " fan. The fucking rat gave me the autograph and told me to burn in hell. Later, when I gave my son the " +
        "autograph he started crying and said he hated me. Turns out the mousefucker didnt write his autograph, no, " +
        "he wrote “you’re a piece of shit, and i fucked your mom”. I’m now divorced, and planning a huge class-action" +
        " lawsuit against the white devil that ruined my life. Your time is almost over, Stuart. All the people " +
        "you’ve wronged will rise against you."

val navy = "What the fuck did you just fucking say about me, you little bitch? I'll have you know I graduated top of" +
        " my class in the Navy Seals, and I've been involved in numerous secret raids on Al-Quaeda, and I have over " +
        "300 confirmed kills. I am trained in gorilla warfare and I'm the top sniper in the entire US armed forces." +
        " You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which" +
        " has never been seen before on this Earth, mark my fucking words. You think you can get away with saying" +
        " that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of " +
        "spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The " +
        "storm that wipes out the pathetic little thing you call your life. You're fucking dead, kid. I can be " +
        "anywhere, anytime, and I can kill you in over seven hundred ways, and that's just with my bare hands. Not " +
        "only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States" +
        " Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent," +
        " you little shit. If only you could have known what unholy retribution your little \"clever\" comment was" +
        " about to bring down upon you, maybe you would have held your fucking tongue. But you couldn't, you didn't, " +
        "and now you're paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. " +
        "You're fucking dead, kiddo."

val order = 6 //This is an arbitrary value for the order of the n-grams
var ngrams : MutableMap< String,ArrayList<Char>> = mutableMapOf() //the list of ngrams and their possible next characters
val logger = Logger("Markov Chain text generator")

//This function loads the values gathered from an input text into a map of n-grams and next characters
fun loadMarkov(input : String){
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
    for(i in 0..(2000-order)){
        currentGram = output.substring(i,i+order)
        //take the ngram and check if it has next characters
        val nextChars = ngrams[currentGram]
        if(nextChars.isNullOrEmpty()) {
            return output}
        //add random character to output and select next n-gram
        output += nextChars.random()
    }
    return output
}