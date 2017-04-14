name                := "Guestbook"

version             := "1.0"

scalaVersion        := "2.11.8"

scalacOptions       += "-deprecation"

libraryDependencies ++= Seq("org.http4s" %% "http4s-dsl" % "0.15.7a",
                            "org.http4s" %% "http4s-circe" % "0.15.7a",
                            "org.http4s" %% "http4s-blaze-client" % "0.15.7a",
                            "org.http4s" %% "http4s-blaze-server" % "0.15.7a",
                            "com.typesafe.slick" %% "slick" % "3.2.0",
                            "org.slf4j" % "slf4j-nop" % "1.6.4",
                            "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0")
