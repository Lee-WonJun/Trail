## Kotlin Trail
Kotlin Trail is a library inspired by the railway-oriented programming concept. It helps you manage data transformation pipelines with custom error handling.

## Caution
This library was created solely for the purpose of exploring the features and capabilities of the Kotlin language and for ***fun***. Therefore, it may not be suitable for actual use in real-world projects. 

While Kotlin Trail shares some similarities with ROP, it should not be seen as a direct implementation of the ROP concept. There are significant differences between the two, particularly in monads and monad comprehension



## Features
- Data transformation pipeline management
- Custom error handling
- Chaining operations using infix functions
- Flexible conversion and stop functions

## Usage
Create a pipeline by defining a sequence of stations. Each station is a transformation function that takes an input and returns an output. You can also provide an optional converter function and a stop function for each station.

```kotlin
    val result = Departure start 10 next
            station(
                fn = { i -> i + 5 },
                stop = { println("Stop at station 1") }
            ) next
            station(
                fn = { i -> i + i },
                converter = { i -> i.toString() },
                stop = { println("Stop at station 2") }
            ) next
            station(
                fn = { i -> i+ i },
                converter = { i -> i.toInt() },
                stop = { println("Stop at station 3") }
            ) next
            station(
                fn = { i -> "Answer: $i" },
                stop = { println("Stop at station 4") }
            ) next
            station(
                fn = { i -> i + 1 },
                converter = { i -> i.toInt() },
                stop = { println("Stop at station 5") }
            ) next
            station(
                fn = { i -> i + 1 },
                stop = { println("Stop at station 6") }
            )
```

## Station Definition
You can define a station using the station function, which takes a function that defines the operation to be performed by the station and optional converters and stop handlers.

converter: A function that takes the input type of the station and returns the type expected by the station function.

stop: A function that handles exceptions during the execution of the station.


```kotlin
Copy code
val station = station(
    fn = { i: Int -> i + 1 },
    converter = { i: String -> i.toInt() },
    stop = { println("Stop at station") }
)
```
1. convert the input to Int
2. execute the fn function
3. if fn throws an exception, execute the stop function.
