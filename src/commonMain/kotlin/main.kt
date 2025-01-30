import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.math.geom.*
import spawner.*

/**
 * This is a game about a house with an elevator. NPCs are being spawned on floors, then they
 * walk towards the elevator. After the elevator is reached, an NPC will enter the elevator.
 * While waiting for the elevator an NPC has a timer. If the timer reaches 0, the NPC will
 * walk away from the elevator and player looses a life.
 */
suspend fun main() = Korge(
    windowSize = Size(1920, 1080),
    backgroundColor = Colors["#2b2b2b"],
) {
    sceneContainer {
        changeTo { InGameScene() }
    }
}

const val FloorHeight = 15

class InGameScene : Scene() {
    private val elevatorButtons = mutableMapOf<Int, View>()
    // Track current floor (1-based to match button numbers)
    private var currentElevatorFloor = 1
    private val timePerFloor = 2000 // 2 second per floor

    private fun Container.ceiling(color: RGBA = Colors.CORNFLOWERBLUE) =
        solidRect(width = 1000, height = 15) {
            this.color = color
        }

    private fun Container.wall(height: Int = 150) =
        solidRect(width = 15, height = height) {
            color = Colors.ROSYBROWN
        }

    private fun Container.floor() =
        solidRect(width = 1000, height = FloorHeight) {
            color = Colors.ANTIQUEWHITE
        }

    // TODO Should we have ceiling + wall + floor OR wall + floor? If we have ceiling - should
    //  we accept "stacking" ceiling/floor in between storeys?
    private fun Container.storey() = container {
//        ceiling()
        wall()
        floor().apply {
            alignTopToBottomOf(wall())
        }
    }

    override suspend fun SContainer.sceneMain() {
        val house = container {
            val storey1 = storey()
            val storey2 = storey()
            val storey3 = storey()
            val storey4 = storey()
            val storey5 = storey().apply {
                ceiling(
                    color = Colors.ANTIQUEWHITE
                ).alignTopToTopOf(this)
            }

            storey5.alignTopToTopOf(this)  // Top floor
            storey4.alignTopToBottomOf(storey5)
            storey3.alignTopToBottomOf(storey4)
            storey2.alignTopToBottomOf(storey3)
            storey1.alignTopToBottomOf(storey2)  // Ground floor

            position(x = 0, y = 50)
        }

        val elevator = elevator()

        // Get the ground floor (storey1) which is the first child of house
        val groundStorey = house.children.firstOrNull()
        if (groundStorey != null) {
            val groundFloor = (groundStorey as Container).children.lastOrNull()
            if (groundFloor != null) {
                elevator.position(
                    x = house.x + groundStorey.x + groundStorey.width,
                    y = house.y + groundStorey.y + groundFloor.y
                )
            }
        }

        container {
            repeat(5) { iteration ->
                val buttonHeight = 96
                val button = solidRect(width = 192, height = buttonHeight) {
                    color = Colors.SEAGREEN
                    position(x = 0, y = (buttonHeight + 24) * iteration)
                }
                button.onClick {
                    val targetFloor = 5 - iteration // Convert iteration to floor number (1-5)
                    val targetStorey = house.children.getOrNull(4 - iteration)
                    if (targetStorey != null) {
                        val floor = (targetStorey as Container).children.lastOrNull()
                        if (floor != null) {
                            val elevatorPositionX = house.x + targetStorey.x + targetStorey.width
                            val elevatorPositionY = house.y + targetStorey.y + floor.y

                            // Calculate travel time based on floors traversed
                            val floorsToTravel = kotlin.math.abs(targetFloor - currentElevatorFloor)
                            val travelTime = floorsToTravel * timePerFloor

                            elevator.move(
                                posX = elevatorPositionX.toInt(),
                                posY = elevatorPositionY.toInt(),
                                timeMs = travelTime
                            )
                            
                            currentElevatorFloor = targetFloor
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

        val passengerSpawner = Spawner(
            passengersCount = 10,
            storeyCount = 5,
            spawnTimeRange = 1000L..4000L,
        )
        passengerSpawner.setOnSpawnAction { spawnedStorey, desiredStorey ->
            println("spawnedStorey = $spawnedStorey")
            val targetStorey = house.children.getOrNull(spawnedStorey.dec())
            val passengersCount = passengerSpawner.getPassengersCount(spawnedStorey)
            targetStorey?.let {
                val firstPassengerX = house.x + targetStorey.x + targetStorey.width - PassengerRectWidth*2
                val firstPassengerY = house.y + targetStorey.y + targetStorey.height - FloorHeight - PassengerRectHeight
                val newPassenger = Passenger(
                    spawnX = firstPassengerX - (passengersCount * PassengerRectWidth) - (passengersCount * PassengersPadding),
                    spawnY = firstPassengerY,
                    spawnStoreyNum = spawnedStorey,
                    desiredStoreyNum = desiredStorey
                ).addTo(this)

                return@setOnSpawnAction newPassenger
            }
        }
        passengerSpawner.startSpawning()
    }
}
