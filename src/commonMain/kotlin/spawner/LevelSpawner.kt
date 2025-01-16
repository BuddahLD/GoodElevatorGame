package spawner

import javax.print.attribute.standard.QueuedJobCount

const val RepeatTimes = 2000
var lock: Any = Any()

class LevelSpawner(
    private var counter: Int = 0
) : Runnable {

    override fun run() {
        repeat(RepeatTimes) {
            takePassengers()
        }
    }

    private fun increment() {
        synchronized(lock) {
            counter++
        }
    }

    private fun takePassengers(elevatorPassengers: ElevatorPassengers) {
        elevatorPassengers.takePassenger()
        counter++
    }
}
