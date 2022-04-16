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

enum Spec {
  case Adverb
  case Adjective
  case Noun
  case Separator(str: String)
}

def generate(spec: Spec*): String = {
  val builder = collection.mutable.StringBuilder()

  def next(words: Array[String]) =
    builder ++= words(util.Random.nextInt(words.length))

  spec.foreach{
    case Spec.Adverb => next(adverbs)
    case Spec.Adjective => next(adjectives)
    case Spec.Noun => next(nouns)
    case Spec.Separator(str) => builder ++= str
  }
  builder.result()
}