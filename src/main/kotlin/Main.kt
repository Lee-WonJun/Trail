object Departure {
    infix fun <T> start(value: T) = TrainArrival.Continue(value)
}

sealed class TrainArrival<T> {
    data class Continue<T>(val stationOutput: T) : TrainArrival<T>()
    class Stop<T> : TrainArrival<T>()
}

typealias StopFn<reified T> = (TrainArrival<T>) -> Unit
typealias StationFn<reified T, reified U> = (T) -> U
typealias ConverterFn<reified T, reified U> = (T) -> U

data class Station<PO, FI, FR>(
    val fn: StationFn<FI, FR>,
    val converter: ConverterFn<PO, FI>,
    val stop: StopFn<PO>
) {
    fun drive(prevOut: TrainArrival<PO>): TrainArrival<FR> = when (prevOut) {
        is TrainArrival.Continue<PO> -> {
            try {
                val stationOutput = fn(converter(prevOut.stationOutput))
                TrainArrival.Continue(stationOutput)
            } catch (e: Exception) {
                stop(prevOut)
                TrainArrival.Stop()
            }
        }
        is TrainArrival.Stop<PO> -> TrainArrival.Stop()
    }
}

infix fun <S1PO, S1FI, S1FR> TrainArrival<S1PO>.next(s1: Station<S1PO, S1FI, S1FR>): TrainArrival<S1FR> = s1.drive(this)

fun <PO, FI, FR> station (
    fn: StationFn<FI, FR>,
    converter: ConverterFn<PO, FI>,
    stop: StopFn<PO> = { _ -> }
) = Station(fn, converter, stop)


fun <PO, FR> station (
    fn: StationFn<PO, FR>,
    stop: StopFn<PO> = { _ -> }
) = Station(fn,  { i:PO -> i }, stop)