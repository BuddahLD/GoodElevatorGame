package spawner

import korlibs.image.color.*
import korlibs.korge.view.*


const val PassengerRectHeight = 100
const val PassengerRectWidth = 10
const val PassengersPadding = 12

class Passenger(
    spawnX: Double,
    spawnY: Double,
    color: RGBA = Colors.DARKOLIVEGREEN,
    val spawnStoreyNum: Int,
    val desiredStoreyNum: Int,
) : Container() {

    private var location: PassengerLocation = PassengerLocation.Floor

    init {
        solidRect(width = PassengerRectWidth, height = PassengerRectHeight, color = color) {
            position(spawnX, spawnY)
        }
    }

    fun changeLocation(newLocation: PassengerLocation) {
        location = newLocation
    }

    fun getLocation() : PassengerLocation = location
}

enum class PassengerLocation {
    Floor, Elevator,
}
