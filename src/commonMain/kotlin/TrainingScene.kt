import korlibs.image.color.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

const val ThreadCount = 5
const val TextBlocksStartX = 0
const val TextBlocksOffset = 40

class TrainingScene : Scene() {

    override suspend fun SContainer.sceneMain() {
//        val crowd = Passenger()
//        val crowdText = text(
//            text = crowd.getCount().toString(),
//            textSize = 30
//        ) {
//            position(x = TextBlocksStartX, y = 50)
//        }
//        val spawners = mutableListOf<Spawner>()
//        var textBlocksList = mutableListOf<Text>()
//        var textBlockXOffset = TextBlocksStartX
//        repeat(ThreadCount) {
//            textBlocksList.add(
//                text(
//                    text = counter.toString(),
//                    textSize = 30
//                ) {
//                    position(x = TextBlocksStartX + textBlockXOffset, y = 0)
//                }
//            )
//            textBlockXOffset += TextBlocksOffset
//        }
//
//        repeat(ThreadCount) { spawners.add(Spawner()) }
//        spawners.forEach { Thread(it).start() }
//        addUpdater {
//            textBlock.text = counter.toString()
//        }
//
        solidRect(
            size = Size(width = 100, height = 200),
            color = Colors.SEAGREEN,
        ) {
            position(x = 230, y = 600)
            rotation(rot = Angle.fromDegrees(45))
        }
    }
}


