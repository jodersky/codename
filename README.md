Utility to generate code names, suitable for placeholders of project
names.

## Building
This is a scala-native project. The binary can be built by running
`./mill codename.nativeLink`. Version information is derived from git, with
initial "v" dropped (e.g. the tag v1.2.3 represents version 1.2.3).

### Download
If you are on Linux on an x86_64 platform, a pre-built binary can be
obtained from the GitHub releases page, or with the following
commands:

```
wget https://github.com/jodersky/codename/releases/download/v0.2.0/codename_linux_amd64.xz -O - | unxz - > codename && chmod +x ./codename
```

## Examples

- `codename "A a n" 6`:

  >earnestly rare clown

  >very windy yaw

  >awful patchy zero

  >henceforth catchy graphite

  >nonethless furious aleph

  >otherwise jumpy route

- `codename "a-n" 3`

  >mellow-gazelle

  >united-five

  >optuse-joke

- `codename "A a n a a n n" 1`

  >approximately inherent omicron fabulous zillion unit set

## Contributing

Contributions are welcome, especially new words! It's hard to describe
what words should be included, but the rule of thumb is to use words
that generally have a low frequency in English text (although not
necessarily archaic), and that sound interesting when said out loud.

## Usage
Refer to the help message of the utility (`codename --help`) for an
authoritative answer.

```
Usage: codename [OPTIONS...] [SPECIFICATION] [NUM]
Generate a random codename according to a specification, a number of times
("A a n" 10 times by default).

Options:

  -h, --help        show help message
  -v, --version     show version information

Specification:

 SPEC ::= { 'A' | 'a' | 'n' | SEP }
 SEP  ::= char

where an 'A' is replaced by a random adverb, an 'a' by an adjective and
an 'n' by a noun.

For example, the specification "A-a-n" will produce a code name such as:
"extra-pickled-umbrella".
```
