package spawner

class ElevatorPassengers(
    @get:Synchronized
    private var count: Int = 500
) {

    fun incrementCount() = count.inc()

    @Synchronized
    fun takePassenger() = count.dec()

    fun getCount() = count
}