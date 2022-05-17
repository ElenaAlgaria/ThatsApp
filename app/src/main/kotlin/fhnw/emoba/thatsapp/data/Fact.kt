package fhnw.emoba.thatsapp.data

import kotlin.random.Random

object Fact {
    private val avo = "Avocados are a fruit, not a vegetable. They're technically considered a single-seeded berry, believe it or not.\n"

    private val eif = "The Eiffel Tower can be 15 cm taller during the summer, due to thermal expansion meaning the iron heats up, the particles gain kinetic energy and take up more space.\n"

    private val aus = "Australia is wider than the moon. The moon sits at 3400km in diameter, while Australia’s diameter from east to west is almost 4000km."

    private val mel = "'Mellifluous' is a sound that is pleasingly smooth and musical to hear."

    private val tee = "Human teeth are the only part of the body that cannot heal themselves. Teeth are coated in enamel which is not a living tissue.\n"

    private val gui = "It's illegal to own just one guinea pig in Switzerland. It's considered animal abuse because they're social beings and get lonely.\n"

    private val toa = "The Ancient Romans used to drop a piece of toast into their wine for good health - hence why we 'raise a toast'.\n"

    private val ven = "Venus is the only planet to spin clockwise. It travels around the sun once every 225 Earth days but it rotates clockwise once every 243 days."

    private val air = "The first airplane flew on December 17, 1903. Wilbur and Orville Wright made four brief flights at Kitty Hawk, North Carolina, with their first powered aircraft, aka the first airplane.\n"

    private val art = "Competetive art used to be an Olympic sport. Between 1912 and 1948, the international sporting events awarded medals for music, painting, sculpture and architecture. Shame it didn't catch on, the famous pottery scene in Ghost could have won an Olympic medal as well as an Academy Award for the best screenplay."

    private val egg = "A chef's hat has 100 pleats. Apparently, it's meant to represent the 100 ways you can cook an egg. Wonder if Gordon Ramsay knows that.\n"

    private val tin = "In 2014, there was a Tinder match in Antarctica. 2 research scientists matched on the global dating app in the most remote part of the world - a man working at the United States Antarctic McMurdo Station and a woman camping a 45-minute helicopter ride away. What are the chances?!"

    private val ant = "The Spanish national anthem has no words. The 'Marcha Real' is one of only four national anthems in the world (along with those of Bosnia and Herzegovina, Kosovo, and San Marino) to have no official lyrics.\n"

    private val lob = "The probability of a blue lobster existing is widely touted as being one in two million. Bright blue lobsters are so-coloured because of a genetic abnormality that causes them to produce more of a certain protein than others.\n"

    private val facts = listOf(avo, eif, aus, mel, tee, gui, toa, ven, air, art, egg, tin, ant, lob)

    private fun List<String>.random() = this[Random.nextInt(1, size)]

    fun generateMsg(): String = "${facts.random()} "

}