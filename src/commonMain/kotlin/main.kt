import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.math.geom.*

/**
 * This is a game about a house with an elevator. NPCs are being spawned on floors, then they
 * walk towards the elevator. After the elevator is reached, an NPC will enter the elevator.
 * While waiting for the elevator an NPC has a timer. If the timer reaches 0, the NPC will
 * walk away from the elevator and player looses a life.
 */
suspend fun main() = Korge(
    windowSize = Size(1920, 1080),
    backgroundColor = Colors["#2b2b2b"]
) {
    sceneContainer {
        changeTo { TrainingScene() }
    }
}

class InGameScene : Scene() {
    private val elevatorButtons = mutableMapOf<Int, View>()

    private fun Container.ceiling() =
        solidRect(width = 1000, height = 15) {
            color = Colors.CORNFLOWERBLUE
        }

    private fun Container.wall(height: Int = 150) =
        solidRect(width = 15, height = height) {
            color = Colors.ROSYBROWN
        }

    private fun Container.floor() =
        solidRect(width = 1000, height = 15) {
            color = Colors.ANTIQUEWHITE
        }

    private fun Container.storey() = container {
        ceiling()
        wall()
        floor()
    }

    override suspend fun SContainer.sceneMain() {
        val house = container {
            val storey1 = storey()
            val storey2 = storey()
            val storey3 = storey()
            val storey4 = storey()
            val storey5 = storey()

            storey2.alignBottomToTopOf(storey3)
            storey3.alignBottomToTopOf(storey4)
            storey4.alignBottomToTopOf(storey5)
            storey5.alignBottomToTopOf(storey1)

            position(x = 0, y = 150)
        }

        val elevator = elevator()
        elevator.alignBottomToBottomOf(house)
        elevator.alignLeftToRightOf(house)

        container {
            repeat(5) { iteration ->
                val buttonHeight = 96
                val button = solidRect(width = 192, height = buttonHeight) {
                    color = Colors.SEAGREEN
                    position(x = 0, y = (buttonHeight + 24) * iteration)
                }
                button.onClick {
                    val elevatorPositionX =
                        house.x + house.children[iteration].width
                    val elevatorPositionY =
                        house.y +
                            house.children[iteration].height +
                            house.children[iteration].y

                    elevator.move(elevatorPositionX.toInt(), elevatorPositionY.toInt())
                }
                elevatorButtons += (5 - iteration) to button
                text("${5 - iteration}") {
                    centerOn(button)
                    color = Colors.BLANCHEDALMOND
                    textSize = 36.0
                }
            }
            position(x = 1400, y = 200)
        }
    }
}
