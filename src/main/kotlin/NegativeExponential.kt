import kotlin.math.pow
import kotlin.random.Random

private val logger = Logger("Negative Exponential")

//The point of this function is to determine a probability that the message generation should continue end on the length
//of the message (in characters)
//The probability is based off the negative exponential variable, with a lambda parameter serving as an average
//Should be called at the end of a word
private fun negEx(lambda : Int, t : Int) : Double{
    return if (t<=0) 0.0 else 1 - (Math.E.pow(-(t.toDouble())/(lambda.toDouble())))
}

//Checks the probability of halting, rolls a random number and checks if the number is higher
fun messageEnd(size : Int, roll : Double) : Boolean{
    val p = negEx(config.averageLength,size)
    logger.debug("For size $size, halting probability : $p")
    logger.debug("Rolled $roll")
    return roll < p
}