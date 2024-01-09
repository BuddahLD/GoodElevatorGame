import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.korge.animate.*
import korlibs.korge.input.*
import korlibs.korge.ui.*
import korlibs.korge.view.align.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.time.*

suspend fun main() = Korge(
    windowSize = Size(1920, 1080),
    backgroundColor = Colors["#2b2b2b"]
) {
    val sceneContainer = sceneContainer()


    sceneContainer.changeTo {
        MyScene()
    }
}

class MyScene : Scene() {
    private var isElevatorMoving: Boolean = false
    private var nextFloor: Int = 0
    private val elevatorButtons = mutableMapOf<Int, View>()

    override suspend fun SContainer.sceneMain() {
        val floorsContainer = container {
            repeat(6) {
                solidRect(width = 900, height = 15) {
                    position(x = 0, y = 150 * it)
                    color = Colors.ANTIQUEWHITE
                }
            }
            position(x = 50, y = 150)
        }

        val elevator = solidRect(width = 100, height = 150) {
            rotation = 0.degrees
//            anchor(.5, .5)
            color = Colors.CORNFLOWERBLUE
            alignBottomToBottomOf(floorsContainer)
            alignLeftToRightOf(floorsContainer)
        }

        container {
            repeat(5) { iteration ->
                val buttonHeight = 96
                val button = solidRect(width = 192, height = buttonHeight) {
                    color = Colors.SEAGREEN
                    position(x = 0, y = (buttonHeight + 24) * iteration)
                }
                button.onClick {
                    if (isElevatorMoving) {
                        nextFloor = (5 - iteration)
                    } else {
                        isElevatorMoving = true
                        elevator.animate {
                            val elevatorPositionX =
                                floorsContainer.x + floorsContainer.children[iteration].width
                            val elevatorPositionY =
                                floorsContainer.y +
                                    floorsContainer.children[iteration].height +
                                    floorsContainer.children[iteration].y

                            moveTo(
                                view = elevator,
                                x = elevatorPositionX,
                                y = elevatorPositionY,
                                time = TimeSpan(milliseconds = 1000.0)
                            )
                            onComplete.add {
                                isElevatorMoving = false
                            }
                        }
                    }
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
