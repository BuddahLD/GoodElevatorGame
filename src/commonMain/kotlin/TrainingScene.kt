import korlibs.image.color.Colors
import korlibs.korge.scene.Scene
import korlibs.korge.view.SContainer
import korlibs.korge.view.Text
import korlibs.korge.view.addUpdater
import korlibs.korge.view.position
import korlibs.korge.view.rotation
import korlibs.korge.view.solidRect
import korlibs.korge.view.text
import korlibs.math.geom.Angle
import korlibs.math.geom.Size
import spawner.ElevatorPassengers
import spawner.LevelSpawner
import spawner.counter

const val ThreadCount = 5
const val TextBlocksStartX = 0
const val TextBlocksOffset = 40

class TrainingScene : Scene() {

    override suspend fun SContainer.sceneMain() {
        val crowd = ElevatorPassengers()
        val crowdText = text(
            text = crowd.getCount().toString(),
            textSize = 30
        ) {
            position(x = TextBlocksStartX, y = 50)
        }
        val spawners = mutableListOf<LevelSpawner>()
        var textBlocksList = mutableListOf<Text>()
        var textBlockXOffset = TextBlocksStartX
        repeat(ThreadCount) {
            textBlocksList.add(
                text(
                    text = counter.toString(),
                    textSize = 30
                ) {
                    position(x = TextBlocksStartX + textBlockXOffset, y = 0)
                }
            )
            textBlockXOffset += TextBlocksOffset
        }

        repeat(ThreadCount) { spawners.add(LevelSpawner()) }
        spawners.forEach { Thread(it).start() }
        addUpdater {
            textBlock.text = counter.toString()
        }

        solidRect(
            size = Size(width = 100, height = 200),
            color = Colors.SEAGREEN,
        ) {
            position(x = 230, y = 600)
            rotation(rot = Angle.fromDegrees(45))
        }
    }
}


