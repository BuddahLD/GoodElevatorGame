import korlibs.image.color.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

class TrainingScene : Scene() {

    override suspend fun SContainer.sceneMain() {
        solidRect(
            size = Size(width = 100, height = 200),
            color = Colors.SEAGREEN,
        ) {
            position(x = 230, y = 600)
            rotation(rot = Angle.fromDegrees(45))
        }
    }
}
