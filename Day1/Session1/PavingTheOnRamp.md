# Session Summary

* The simplest Java program used to be

        public class Hello {
            public static void main(String[] args) {
                System.out.println("Hello, World!");
            }
        }
        
* Now it is

        void main() {
            IO.println("Hello, World!");
        }
        
* That's much closer to Python's

        print('Hello, World!')
        
* And it is more regular. In compact source files, everything is in a method.
* Every "library" method starts with a class name: `IO.println`, `Math.sqrt`
* The java.base module is always imported
* Another advantage for students: compile-time typing
* Not necessary to start with IntelliJ
* jshell may not be student friendly, but JTaccuino is
* Big reason for use of Python in education: Bindings for data analysis, ML
* More work to put together cool student projects in Java
* Discussion about minimal subset for first course. Compact classes, records, lists (but not arrays), maps, interfaces. Classes can come later. 
* Would be nice if there was a List.ofMutable
* Should one teach (classic) `switch` to beginners?

# Links

* [JEP 511](https://openjdk.org/jeps/511): Module Import Declarations
* [JEP 512](https://openjdk.org/jeps/512): Compact Source Files and Instance Main Methods
* [Brian Goetz, Paving the on-ramp](https://openjdk.org/projects/amber/design-notes/on-ramp)
* [JTaccuino](https://github.com/jtaccuino/jtaccuino)
