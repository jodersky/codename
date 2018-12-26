Utility to generate code names, suitable for placeholders of project
names.

## Building
This is a scala-native project. The binary can be built by running
`sbt nativeLink`. Version information is derived from git, with
initial "v" dropped (e.g. the tag v1.2.3 represents version 1.2.3).

## Usage
Refer to the help message of the utility (`codename --help`) for an
authoritative answer.

```
Usage: codename [OPTIONS...] [SPECIFICATION...]
Generate a random codename according to a specification ("A a n" by default).

Options:

  -h, --help        show help message
  -v, --version     show version information

Specification:

 SPEC ::= { 'A' | 'a' | 'n' | SEP }
 SEP  ::= char

where an 'A' is replaced by a random adverb, an 'a' by an adjective and
an 'n' by a noun.

For example, the specification "A-a-n" will produce a code name such as:
"extra-pickled-umbrella"

Multiple specifications may be given, each of which will be printed on a
separate line.
```
