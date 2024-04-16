import korlibs.image.color.*
import korlibs.korge.view.*

class Floor(
    val floorHeight: Int = 150,
    val floorWidth: Int = 900,
    val floorThickness: Int = 15,
): Container() {

    init {
        solidRect(width = floorWidth, height = floorThickness) {
            color = Colors.ANTIQUEWHITE
            position(x = 0, y = floorHeight)
        }
    }
}
