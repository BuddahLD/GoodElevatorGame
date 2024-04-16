interface INpc {
    val speed: Int
    val heightPx: Int
    val positionX: Int
    val floor: Int

    fun walkIntoElevator()
}

// val - value
// var - variable

class Npc(
    override val speed: Int = 5,
    override val heightPx: Int = 20,
    override val positionX: Int,
    override val floor: Int,
) : INpc {

    override fun walkIntoElevator() {

    }
}

fun hello() {
    val npc = Npc(
        positionX = 100,
        floor = 3,
    )
}

// Npc   <---->   Elevator
