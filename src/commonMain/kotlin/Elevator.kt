import korlibs.image.color.*
import korlibs.korge.animate.*
import korlibs.korge.view.*
import korlibs.time.*

fun Container.elevator() = Elevator().addTo(this)

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
    suspend fun move(posX: Int, posY: Int, time: Int = 1000) {
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
                    time = TimeSpan(milliseconds = time.toDouble())
                )
                onComplete.add {
                    isMoving = false
                }
            }
        }
    }
}
