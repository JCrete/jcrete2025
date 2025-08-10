# Session Summary

* Could be nice to have JSON support in the JDK
* For simple scripts, student programs, testing
* Jackson is too complicated (even Jackson Jr.)
* Of course lots of other libraries (see e.g. at the bottom of the JSON spec)
  * Peter Lawrey: Mine isn't even on there
* Dormant JEP
* No data binding (except to lists, maps, maybe records)
* No JSON extensions (no comments, no trailing commas, no single quotes, etc.)
* No schema
* No streaming
* Didn't this go badly with XML?
  * JDK now has baked in ancient XML libraries
  * But JSON is much simpler
* May 2025 proposal by Paul Sandoz
  * Uses interfaces, not records
  * Awaiting deconstructor patterns for full usability
  * No sealed hierarchy :-(
  * Sticky widget: JSON numbers
  * Immutable, but without support for transformations
  * Passes all of JSONTestSuite except for two cases with repeated keys (by design)
  * Rémi has an object mapper extension 
  * Simon Massey has a backport to Java 21 


# References

* [Jackson](https://github.com/FasterXML/jackson)
* [JSON Spec](https://www.json.org/json-en.html)
* [JEP 198](https://openjdk.org/jeps/198): Light-Weight JSON API
* [Paul Sandoz proposal](https://mail.openjdk.org/pipermail/core-libs-dev/2025-May/145905.html)
* [Javadoc](https://cr.openjdk.org/~naoto/json/javadoc/api/java.base/java/util/json/package-summary.html)
* [Code](https://github.com/openjdk/jdk-sandbox/tree/json/src/java.base/share/classes/java/util/json)
* [Rémi Forax' Object mapper](https://github.com/forax/json-object-mapper)
* [Simon Massey's Java 21 port](https://github.com/simbo1905/java.util.json.Java21)
* [JSONTestSuite](https://github.com/nst/JSONTestSuite)
