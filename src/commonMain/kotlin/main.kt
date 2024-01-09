import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.animate.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.math.geom.*
import korlibs.time.*

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

        val elevator = elevator()
        elevator.alignBottomToBottomOf(floorsContainer)
        elevator.alignLeftToRightOf(floorsContainer)

        container {
            repeat(5) { iteration ->
                val buttonHeight = 96
                val button = solidRect(width = 192, height = buttonHeight) {
                    color = Colors.SEAGREEN
                    position(x = 0, y = (buttonHeight + 24) * iteration)
                }
                button.onClick {
                    val elevatorPositionX =
                        floorsContainer.x + floorsContainer.children[iteration].width
                    val elevatorPositionY =
                        floorsContainer.y +
                            floorsContainer.children[iteration].height +
                            floorsContainer.children[iteration].y

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
