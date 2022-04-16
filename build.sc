import mill._, scalalib._, scalanativelib._

def gitVersion = T.input{
  os.proc("git", "describe", "--always", "--dirty=-SNAPSHOT", "--match=v[0-9].*")
    .call().out.text().tail.trim
}

object codename extends ScalaNativeModule {
  def scalaVersion = "3.1.2"
  def scalaNativeVersion = "0.4.4"

  def releaseMode = scalanativelib.api.ReleaseMode.ReleaseFull

  def generatedSources = T {
    val content =
      s"""|object BuildInfo {
          |  val version: String = "${gitVersion()}"
          |}""".stripMargin
    os.write(T.dest / "BuildInfo.scala", content)
    super.generatedSources() ++ Seq(PathRef(T.dest / "BuildInfo.scala"))
  }

  def link = T {
    PathRef(os.Path(scalaNativeWorker().nativeLink(nativeConfig(), (T.dest / "out").toIO)))
  }

  def strip = T {
    os.copy(link().path, T.dest / "out")
    os.proc("strip", T.dest / "out").call()
    PathRef(T.dest / "out")
  }
}

// assumes that this is run on linux_amd64
def dist() = T.command {
  val dest = os.pwd / "dist" / gitVersion()
  os.remove.all(dest)
  os.makeDir.all(dest)

  os.proc("xz", "--stdout", "-k", codename.strip().path)
    .call(stdout = dest / "codename_linux_amd64.xz")
  os.proc("gzip", "--stdout", "-k", codename.strip().path)
    .call(stdout = dest / "codename_linux_amd64.gz")

  os.proc("sha256sum", "codename_linux_amd64.xz", "codename_linux_amd64.gz")
    .call(cwd = dest, stdout = dest / "SHA256SUMS")

  os.proc("gpg", "--armor", "--detach-sign", "SHA256SUMS")
    .call(cwd = dest)
}
