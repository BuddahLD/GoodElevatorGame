package spawner

import korlibs.image.color.*
import korlibs.korge.render.*
import korlibs.korge.view.*


const val PassengerRectHeight = 100
const val PassengerRectWidth = 10
const val PassengersPadding = 12

class Passenger(
    val info: Info
) : RectBase() {

    private var location: PassengerLocation = PassengerLocation.Floor

    init {
        SolidRect(width = PassengerRectWidth, height = PassengerRectHeight, color = info.color)
    }

    fun changeLocation(newLocation: PassengerLocation) {
        location = newLocation
    }

    fun getLocation() : PassengerLocation = location

    data class Info(
        val id: String,
        val color: RGBA = Colors.DARKOLIVEGREEN,
        val currentStorey: Int,
        // TODO remove desiredStorey and it's references
        val desiredStorey: Int = 1,
    )
}

sealed class PassengerLocation {
    data class Floor(val x: Long = 0, val y: Long = 0) : PassengerLocation()
    data object Elevator : PassengerLocation()
}
