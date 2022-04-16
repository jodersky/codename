package codenames

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

val help =
  """|Usage: codename [OPTIONS...] [SPECIFICATION] [NUM]
     |Generate a random codename according to a specification, a number of times
     |("A a n" 10 times by default).
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
     |"extra-pickled-umbrella".""".stripMargin

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

  val (spec: String, num: Int) = arguments match {
    case Array() => ("A a n", 10)
    case Array(spec) => (spec, 10)
    case Array(spec, num) =>
      val n = num.toIntOption match {
        case Some(n) =>
          n
        case None =>
          System.err.println(s"Invalid number of repetitions: '${num}' is not an integer")
          sys.exit(1)
      }
      (spec, n)
  }

  def printNext(words: Array[String]) =
    print(words(util.Random.nextInt(words.length)))

  for (_ <- 0 until num) {
    spec foreach {
      case 'A'   => printNext(adverbs)
      case 'a'   => printNext(adjectives)
      case 'n'   => printNext(nouns)
      case other => print(other)
    }
    println("")
  }
}
