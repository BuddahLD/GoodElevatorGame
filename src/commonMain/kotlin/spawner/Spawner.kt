package spawner

import korlibs.io.util.*
import kotlin.concurrent.*
import kotlin.random.*

var lock: Any = Any()

class Spawner(
    private var passengersCount: Int = 0,
    private val storeyNumber: Int = 0,
    private val spawnTimeRange: LongRange
) {

    private val spawnedPassengers: MutableList<SpawnedPassenger> = mutableListOf()
    private var onSpawnAction: ((Passenger.Info) -> Unit)? = null
    private var lastSpawnedPassengerId: Int = 0
    var startSpawn: Boolean = false

    fun initSpawner() {
        thread {
            while (startSpawn && spawnedPassengers.size != passengersCount) {
                Thread.sleep(spawnTimeRange.random())
                spawnPassenger()
            }
        }
    }

    @Synchronized
    private fun spawnPassenger() {
//        synchronized(lock) {
        onSpawnAction?.let { onSpawn ->
            // In one iteration check all storeys and spawn on first found free storey
            val randomStorey = (2..storeyNumber).random()
            if (getPassengersCount(randomStorey)) {

            }
            val randomDesiredStorey = (1..storeyNumber).generateRandomStoreyNumber(randomStorey)
            val randomX = // Random x
            val randomY = // Random x
            val info = Passenger.Info(
                id = UUID.randomUUID().toString(),
                currentStorey = randomStorey,
                desiredStorey = randomDesiredStorey,
            )
            onSpawn(info)

            spawnedPassengers.add(SpawnedPassenger(lastSpawnedPassengerId, info))
            lastSpawnedPassengerId += 1
        }
//        }
    }

    fun setOnSpawnAction(onSpawn: (Passenger.Info) -> Unit) {
        this.onSpawnAction = onSpawn
    }

    fun getPassengersCount(storeyNumber: Int? = null): Int {
        return if (storeyNumber == null || storeyNumber > this.storeyNumber) {
            spawnedPassengers.size
        } else {
            val filteredList = spawnedPassengers.filter {
                it.passenger.info.currentStorey == storeyNumber && it.passenger.getLocation() == PassengerLocation.Floor
            }
            filteredList.size
        }
    }
}

// TODO need more love and passion to this function, but for now let it be 1st storey all the time
fun IntRange.generateRandomStoreyNumber(storeyToExclude: Int): Int {
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
