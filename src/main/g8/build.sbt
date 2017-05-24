organization in ThisBuild := "com.knoldus"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val cassandraApi = "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0"
val mockito = "org.mockito" % "mockito-all" % "1.10.19" % Test

lazy val `producer-api` = (project in file("producer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `producer-impl` = (project in file("producer-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      macwire,
      scalaTest,
      mockito,
      "org.twitter4j" % "twitter4j-core" % "4.0.6"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`producer-api`)

lazy val `consumer-api` = (project in file("consumer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
  .dependsOn(`producer-api`)

lazy val `consumer-impl` = (project in file("consumer-impl"))
  .enablePlugins(LagomScala)
  .settings(lagomForkedTestSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      macwire,
      mockito,
      scalaTest
    )
  )
  .dependsOn(`consumer-api`)

// scoverage exludes files configuration according to projects
coverageExcludedPackages in `producer-impl` :=
  """com.knoldus.producer.impl.util.TwitterUtil.*;
    |com.knoldus.producer.impl.TwitterProducerLoader.*;com.knoldus.producer.impl.TwitterProducerComponents.*;
    |com.knoldus.producer.impl.TwitterProducerApplication.*;""".stripMargin

coverageExcludedPackages in `consumer-impl` :=
  """com.knoldus.consumer.impl.TwitterConsumerLoader;com.knoldus.consumer.impl.TwitterConsumerComponents;
    |com.knoldus.consumer.impl.TwitterConsumerApplication;com.knoldus.consumer.impl.events.TweetEvent;
  """.stripMargin

// End => scoverage exludes files configuration according to projects