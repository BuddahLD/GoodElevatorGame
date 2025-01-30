import korlibs.image.color.*
import korlibs.korge.animate.*
import korlibs.korge.view.*
import korlibs.time.*

fun Container.elevator() = Elevator().addTo(this)

interface IElevator {
    val speed: Int
    val peopleCount: Int
    val color: String

    fun drive(floor: Int)
    fun stop()
    fun help()
}

class Elevator2 : IElevator {
    override val speed: Int = 5
    override val peopleCount: Int = 0
    override val color: String = "red"

    override fun drive(floor: Int) {
        println("Поїхали на поверх $floor")
    }

    override fun stop() {
        println("Стоп")
    }

    override fun help() {
        println("Допомога")
    }

    companion object {
        private const val MAX_PEOPLE_COUNT = 7
    }
}

class Elevator(
    elevatorWidth: Int = 100,
    elevatorHeight: Int = 150,
) : Container() {

    private var isMoving: Boolean = false
    private var nextFloor: Int = 0

    init {
        solidRect(
            width = elevatorWidth,
            height = elevatorHeight,
            color = Colors.CORNFLOWERBLUE
        )
    }

    // TODO make move function accept floors instead of position
    suspend fun move(posX: Int, posY: Int, timeMs: Int = 1000) {
        if (isMoving) {
            // nextFloor = (5 - iteration)
            // when floor is added as an argument - implement this `if` logic
        } else {
            isMoving = true
            animate {
                moveTo(
                    view = this@Elevator,
                    x = posX,
                    y = posY,
                    time = TimeSpan(milliseconds = timeMs.toDouble())
                )
                onComplete.add {
                    isMoving = false
                }
            }
        }
    }
}
