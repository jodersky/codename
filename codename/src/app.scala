val help =
  """|Usage: codename [OPTIONS...] [SPECIFICATION] [NUM]
     |Generate a random codename according to a specification, a number of times
     |("A a n" 10 times by default).
     |
     |Options:
     |
     |  -h, --help        show help message
     |  -v, --version     show version information
     |  --entropy         show entropy information instead of generating code
     |                    names
     |
     |Specification:
     |
     |  SPEC ::= { 'A' | 'a' | 'n' | 'd' | 'g' | SEP }
     |  SEP  ::= char
     |
     |where the quoted letters will be substituted randomly by the following:
     |
     |  A: an adverb
     |  a: an adjective
     |  n: a nound
     |  d: a digit [0-9]
     |  g: a greek letter (name)
     |
     |For example, the specification "A-a-n" will produce a code name such as:
     |"extra-pickled-umbrella".""".stripMargin

def main(args: Array[String]): Unit = {
  val (options, arguments) = args.partition(_.startsWith("-"))

  var action = "generate"

  options foreach {
    case "-h" | "--help" =>
      println(help)
      sys.exit(0)
    case "-v" | "--version" =>
      println(BuildInfo.version)
      sys.exit(0)
    case "--entropy" =>
      action = "entropy"
    case x =>
      System.err.println(s"Invalid option '${x}'.")
      sys.exit(1)
  }

  val (spec0: String, num: Int) = arguments match {
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

  val spec1 = spec0.map{
    case 'A'   => codenames.Spec.Adverb
    case 'a'   => codenames.Spec.Adjective
    case 'n'   => codenames.Spec.Noun
    case 'd'   => codenames.Spec.Digit
    case 'g'   => codenames.Spec.Greek
    case other => codenames.Spec.Separator(other.toString)
  }

  if (action == "generate") {
    for (_ <- 0 until num) {
      println(codenames.generate(spec1: _*))
    }
  } else if (action == "entropy") {
    println(f"${codenames.entropy(spec1: _*)}%.2f")
  } else {
    sys.error("unknown action " + action)
  }
}
