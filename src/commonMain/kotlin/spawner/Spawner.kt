package spawner

import kotlin.concurrent.*
import kotlin.random.*

var lock: Any = Any()

class Spawner(
    private var passengersCount: Int = 0,
    private val storeyCount: Int = 0,
    private val spawnTimeRange: LongRange
) {

    private val spawnedPassengers: MutableList<SpawnedPassenger> = mutableListOf()
    private var onSpawnAction: ((Int, Int) -> Passenger?)? = null
    private var lastSpawnedPassengerId: Int = 0

    fun startSpawning() {
        thread {
            while (spawnedPassengers.size != passengersCount) {
                Thread.sleep(spawnTimeRange.random())
                spawnPassenger()
            }
        }
    }

    private fun spawnPassenger() {
        synchronized(lock) {
            onSpawnAction?.let { action ->
                val spawnedStoreyNumber = (2..storeyCount).random()
                val desiredStoreyNumber = (1..storeyCount).generateRandomStoreyNumber(spawnedStoreyNumber)
                val newPassenger = action(spawnedStoreyNumber, desiredStoreyNumber)
                newPassenger?.let {
                    spawnedPassengers.add(SpawnedPassenger(lastSpawnedPassengerId, newPassenger))
                    lastSpawnedPassengerId += 1
                }
            }
        }
    }

    fun setOnSpawnAction(onSpawnAction: ((Int, Int) -> Passenger?)) {
        this.onSpawnAction = onSpawnAction
    }

    fun getPassengersCount(storeyNumber: Int? = null) : Int {
        return if (storeyNumber == null || storeyNumber > storeyCount) {
            spawnedPassengers.size
        } else {
            val filteredList = spawnedPassengers.filter {
                it.passenger.spawnStoreyNum == storeyNumber && it.passenger.getLocation() == PassengerLocation.Floor
            }
            filteredList.size
        }
    }
}

// TODO need more love and passion to this function, but for now let it be 1st storey all the time
fun IntRange.generateRandomStoreyNumber(storeyToExclude: Int) : Int {
    var returnedStoreyNumber = this.random()

    if (returnedStoreyNumber == storeyToExclude) {
        if (Random.nextBoolean()) {
            returnedStoreyNumber = (1..storeyToExclude.dec()).random()
        } else {
            returnedStoreyNumber = (storeyToExclude.inc()..this.last).random()
        }
    }

    return 1
}

data class SpawnedPassenger(val passengerId: Int, val passenger: Passenger)
