package codenames

val adverbs = Array(
  "abundantly",
  "across",
  "amiss",
  "approximately",
  "awful",
  "awry",
  "basic",
  // "beforehand",
  "earnestly",
  "essentially",
  "even",
  "extra",
  "flat",
  "forthwile",
  "greatly",
  "henceforth",
  "hereinafter",
  "hitherto",
  "imminent",
  "indeed",
  "infra",
  "inter",
  "low",
  "ludicrously",
  "master",
  "nearby",
  "neither",
  "never",
  "newly",
  "nigh",
  "nonethless",
  "not",
  "otherwise",
  "overall",
  "quick",
  "real",
  "soon",
  "sparsely",
  "then",
  "thereafter",
  "throughout",
  "too",
  "upright",
  "very"
)

val adjectives = Array(
  "absurd",
  "accomplished",
  "adjacent",
  "arbitrary",
  "astral",
  "axial",
  "azure",
  "blinking",
  "braided",
  "catchy",
  "closed",
  "complex",
  "considerate",
  "credible",
  "cromulent",
  "dark",
  "essential",
  "extraneous",
  "extreme",
  "fabulous",
  "false",
  "ferocious",
  "flashy",
  "forbidden",
  "furious",
  "glacial",
  "grand",
  "grandiose",
  "great",
  "hidden",
  "high",
  "imaginary",
  "imminent",
  "inherent",
  "intrinsic",
  "invidious",
  "jovial",
  "jumpy",
  "light",
  "little",
  "low",
  "ludicrous",
  "lunar",
  "major",
  "mellow",
  "minor",
  "negative",
  "new",
  "nodal",
  "open",
  "optimistic",
  "optuse",
  "patchy",
  "penurious",
  "perplex",
  "pickled",
  "polar",
  "positive",
  "private",
  "rare",
  "random",
  "rigorous",
  "sentient",
  "serene",
  "serious",
  "sigmoid",
  "solar",
  "sparse",
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
  // "yay",
  "zillion",
  "zoic"
)

val nouns = Array(
  "abyss",
  "aleph",
  "analyst",
  "azimuth",
  "bet",
  "bingo",
  "cat",
  "catch",
  "cigar",
  "clone",
  "clown",
  "code",
  "condor",
  "conjugate",
  "cow",
  "crow",
  "dark",
  "eagle",
  "echo",
  "edition",
  "emu",
  "flume",
  "fountain",
  "giraffe",
  "graphite",
  "gazelle",
  "hall",
  "high",
  "icicle",
  "index",
  "jack",
  "jar",
  "jiffy",
  "jest",
  "joke",
  "joker",
  "khi",
  "king",
  "kite",
  "light",
  "label",
  "lima",
  "loop",
  "low",
  "macro",
  "mage",
  "module",
  "mutton",
  "nest",
  "nil",
  "nomenclature",
  "ostrich",
  "overnumerousness",
  "parcel",
  "pin",
  "pyrite",
  "quartz",
  "queen",
  "quill",
  "radish",
  "resource",
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
  "tag",
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
  "zulu"
)

val greek = Array(
  "alpha",
  "beta",
  "gamma",
  "delta",
  "epsilon",
  "zeta",
  "eta",
  "theta",
  "iota",
  "kappa",
  "lambda",
  "mu",
  "nu",
  "xi",
  "omicron",
  "pi",
  "rho",
  "sigma",
  "tau",
  "upsilon",
  "phi",
  "chi",
  "psi",
  "omega"
)

enum Spec {
  case Adverb
  case Adjective
  case Noun
  case Separator(str: String)
  case Digit
  case Greek
}

class SecureRandom() {

  def nextInt(): Long = {
    val ch = java.nio.channels.FileChannel.open(
      java.nio.file.Paths.get("/dev/urandom"),
      java.nio.file.StandardOpenOption.READ
    )
    val bytes = java.nio.ByteBuffer.allocate(4)
    try {
      if (ch.read(bytes) != 4) sys.error("didn't read as many bytes as expected")
    } finally {
      ch.close()
    }
    var r = 0L
    r |= (bytes.get(0) & 0xffL) << 24
    r |= (bytes.get(1) & 0xffL) << 16
    r |= (bytes.get(2) & 0xffL) << 8
    r |= (bytes.get(3) & 0xffL)
    r
  }

  val MaxInt: Long = 0xffffffffL

  @annotation.tailrec
  final def nextInt(limit: Int): Int = {
    val remainder = MaxInt % limit
    val n = nextInt()
    if (n > MaxInt - remainder) {
      nextInt(limit)
    } else {
      (n % limit).toInt
    }
  }
}

val random = SecureRandom()

def generate(spec: Spec*): String = {
  val builder = collection.mutable.StringBuilder()

  def next(words: Array[String]) =
    builder ++= words(random.nextInt(words.length))

  spec.foreach{
    case Spec.Adverb => next(adverbs)
    case Spec.Adjective => next(adjectives)
    case Spec.Noun => next(nouns)
    case Spec.Separator(str) => builder ++= str
    case Spec.Digit => builder ++= random.nextInt(10).toString
    case Spec.Greek => next(greek)

  }
  builder.result()
}

def entropy(spec: Spec*): Double = {
  val partials = spec.map{
    case Spec.Adverb =>
      math.log(adverbs.length) / math.log(2)
    case Spec.Adjective =>
      math.log(adjectives.length) / math.log(2)
    case Spec.Noun =>
      math.log(nouns.length) / math.log(2)
    case Spec.Digit =>
      math.log(10) / math.log(2)
    case Spec.Greek =>
      math.log(greek.length) / math.log(2)
    case _ => 0
  }
  partials.foldLeft(0.0)(_ + _)
}
