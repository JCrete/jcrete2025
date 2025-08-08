# Session Summary

* Collectors provide extension point for terminal stream ops 
* Gatherers provide extension point for intermediate stream ops
* API is a bit different
* No characteristics, instead use `Gatherer.of`/`Gatherer.ofSequential` and `Gatherer.Integrator.of`/`Gatherer.Integrator.ofGreedy`
* Reviewed a couple of useful gatherers and discussed how they can be implemented
* Only use `Gatherer.of` when the gatherer can be parallelized. You'll know because you must provide a combiner.
* Obviously parallel gatherers are important for performance with parallel streams
* Use `ofGreedy` when the gatherer doesn't short-circuit
* Why is that important?
* Can activate push (forEachRemaining) instead of pull (tryAdvance) on spliterator
* Viktor Klang: Greedy allows for less signal tracking which in aggregate over several operations can have a noticeable advantage. Keep in mind that this is primarily around sequential performance, which in my experience is vastly more common than parallel streams.
* Seems that can't make a big difference in performance (at least for array backed streams)
* Doesn't have anything to do with infinite streams
* Can it lead to better jitted code?
* We tried looking at some simpler jitted code and couldn't come to firm conclusions (other than that it takes a very long time for the JIT to kick in)
* Sven Woltman's experiment: A parallel stream before a serial gatherer has odd pattern of thread usage
* We looked at the source code of GathererOp, in particular the Hybrid implementation
* Key insight: When the gatherer is not greedy, the preceding parallel pipeline is not actually parallel
* Because it might be expensive to speculatively generate elements that are later not needed due to short circuiting
* That's the solution to the mystery
* There are other mysteries, and the Java API source code is very complex. More insights are needed.

# Links

* 
* [Cay Horstmann's Unblog](https://horstmann.com/unblog/2024-10-01/index.html)
* [Heinz Kabutz](https://www.javaspecialists.eu/archive/Issue326-Stream-Gathering-with-a-Different-Distinct-Function.html) Stream Gathering with a Different Distinct Function
* [Basel One presentation](https://horstmann.com/presentations/2024/baselone/)
* [Brian Goetz](https://developer.ibm.com/articles/j-java-streams-3-brian-goetz/) Streams under the hood
* [Sven Woltmann's code example](https://github.com/SvenWoltmann/stream-gatherers)
* [An interesting bug](https://stackoverflow.com/questions/79622707/why-doesnt-my-gatherer-short-circuit-the-stream-if-the-source-is-an-intstream)
