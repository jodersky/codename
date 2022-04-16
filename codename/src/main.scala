package codenames

object Names {

  val adverbs = Array(
    "abundantly",
    "across",
    "amiss",
    "approximately",
    "awful",
    "awry",
    "basic",
    "beforehand",
    "earnestly",
    "even",
    "extra",
    "flat",
    "forthwile",
    "greatly",
    "henceforth",
    "hereinafter",
    "hitherto",
    "imminent",
    "infra",
    "inter",
    "low",
    "master",
    "nearby",
    "neither",
    "never",
    "nigh",
    "nonethless",
    "not",
    "otherwise",
    "overall",
    "quick",
    "real",
    "soon",
    "then",
    "thereafter",
    "throughout",
    "too",
    "upright",
    "very"
  )

  val adjectives = Array(
    "absurd",
    "adjacent",
    "astral",
    "axial",
    "azure",
    "blinking",
    "catchy",
    "closed",
    "complex",
    "dark",
    "extreme",
    "fabulous",
    "false",
    "ferocious",
    "flashy",
    "forbidden",
    "furious",
    "glacial",
    "grand",
    "great",
    "hidden",
    "high",
    "imaginary",
    "inherent",
    "intrinsic",
    "jumpy",
    "light",
    "little",
    "low",
    "mellow",
    "nodal",
    "open",
    "optimistic",
    "optuse",
    "patchy",
    "penurious",
    "perplex",
    "pickled",
    "polar",
    "private",
    "rare",
    "sentient",
    "serene",
    "sigmoid",
    "specific",
    "stable",
    "steep",
    "top",
    "true",
    "ubiquitous",
    "unbalanced",
    "united",
    "universal",
    "vast",
    "vindictive",
    "voodoo",
    "whitty",
    "wide",
    "wimpy",
    "windy",
    "xenial",
    "xenolithic",
    "yay",
    "zillion",
    "zoic"
  )

  val nouns = Array(
    "aleph",
    "azimuth",
    "bet",
    "bingo",
    "cat",
    "catch",
    "cigar",
    "clone",
    "clown",
    "condor",
    "conjugate",
    "cow",
    "crow",
    "dark",
    "delta",
    "eagle",
    "echo",
    "edition",
    "emu",
    "five",
    "flume",
    "fountain",
    "graphite",
    "gazelle",
    "high",
    "index",
    "jack",
    "jar",
    "jiffy",
    "joke",
    "khi",
    "king",
    "kite",
    "light",
    "lima",
    "loop",
    "low",
    "macro",
    "module",
    "mutton",
    "nest",
    "nil",
    "nomenclature",
    "omicron",
    "one",
    "open",
    "ostrich",
    "parcel",
    "phi",
    "pin",
    "psi",
    "pyrite",
    "quartz",
    "queen",
    "quill",
    "resource",
    "rho",
    "rick",
    "route",
    "rules",
    "sentinel",
    "set",
    "sheep",
    "sigma",
    "six",
    "slam",
    "smoke",
    "source",
    "spinel",
    "thunder",
    "top",
    "topaz",
    "tulip",
    "umbrella",
    "unit",
    "user",
    "vow",
    "wolf",
    "xenium",
    "yaw",
    "yield",
    "zenith",
    "zero",
    "zulu"
  )

}

object Main {

  val help =
    """|Usage: codename [OPTIONS...] [SPECIFICATION...]
       |Generate a random codename according to a specification ("A a n" by default).
       |
       |Options:
       |
       |  -h, --help        show help message
       |  -v, --version     show version information
       |
       |Specification:
       |
       | SPEC ::= { 'A' | 'a' | 'n' | SEP }
       | SEP  ::= char
       |
       |where an 'A' is replaced by a random adverb, an 'a' by an adjective and
       |an 'n' by a noun.
       |
       |For example, the specification "A-a-n" will produce a code name such as:
       |"extra-pickled-umbrella"
       |
       |Multiple specifications may be given, each of which will be printed on a
       |separate line.""".stripMargin

  def main(args: Array[String]): Unit = {

    val (options, arguments) = args.partition(_.startsWith("-"))

    options foreach {
      case "-h" | "--help" =>
        println(help)
        sys.exit(0)
      case "-v" | "--version" =>
        println(BuildInfo.version)
        sys.exit(0)
      case x =>
        System.err.println(s"Invalid option '${x}'.")
        sys.exit(1)
    }

    val withDefault = if (arguments.isEmpty) Array("A a n") else arguments

    def printNext(words: Array[String]) =
      print(words(util.Random.nextInt(words.length)))

    withDefault foreach { arg =>
      arg foreach {
        case 'A'   => printNext(Names.adverbs)
        case 'a'   => printNext(Names.adjectives)
        case 'n'   => printNext(Names.nouns)
        case other => print(other)
      }
      println("")
    }
  }

}
