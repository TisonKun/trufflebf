# TruffleBF

TruffleBF is a brainfuck implementation on Truffle Framework.

## Prerequisites

- [GraalVM 19.0.0+](http://www.graalvm.org/docs/getting-started/)
- Maven 3.0.1+

## How to run

1. `git clone https://github.com/TisonKun/trufflebf`
2. `mvn package -DskipTests`
3. Execute `./bf examples/helloworld.bf` to run a Hello World bf program.

## Install TruffleBF into GraalVM

```
$ mvn package -DskipTests
$ gu install -F install component/bf-component.jar
```

### Interop between JavaScript and TruffleBF

`examples/interop.js` shows an example which interops bettern JavaScript
and TruffleBF.

```
$ node --polyglot --jvm examples/interop.js
```

### Interop between Java and TruffleBF

`examples/Interop.java` shows an example which interops bettern Java and
TruffleBF.

```
$ javac examples/Interop.java
$ java examples.Interop
```

### Interop between C and TruffleBF

`examples/interop.c` shows an example which interops bettern C and
TruffleBF.

```
$ clang -g -O1 -c -emit-llvm -I/path/to/graalvm/jre/languages/llvm examples/interop.c
$ lli --polyglot --jvm interop.bc
```
### Interop between Ruby and TruffleBF


`examples/interop.rb` shows an example which interops bettern Ruby and
TruffleBF.

```
$ ruby --experimental-options --single-threaded --polyglot --jvm examples/interop.rb
Input 2 nonnegative numbers
<your first number, e.g., 42>
<your second number, e.g., 21>
[the result]
```

