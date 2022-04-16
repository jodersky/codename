import mill._, scalalib._, scalanativelib._

object codename extends ScalaNativeModule {
  def scalaVersion = "3.1.2"
  def scalaNativeVersion = "0.4.4"

  def gitVersion = T.input{
    os.proc("git", "describe", "--always", "--dirty=-SNAPSHOT", "--match=v[0-9].*")
      .call().out.text().tail.trim
  }

  def releaseMode = scalanativelib.api.ReleaseMode.ReleaseFull

  def generatedSources = T {
    val content =
      s"""|package codenames
          |object BuildInfo {
          |  val version: String = "${gitVersion()}"
          |}""".stripMargin
    os.write(T.dest / "BuildInfo.scala", content)
    super.generatedSources() ++ Seq(PathRef(T.dest / "BuildInfo.scala"))
  }
}
