plugins {
  id("buildlogic.java-conventions")
}

dependencies {
  implementation("org.yaml:snakeyaml:1.33")
  implementation("commons-io:commons-io:2.16.1")
  implementation("commons-lang:commons-lang:2.6")
  implementation("org.slf4j:slf4j-api:1.7.36")
  implementation("com.google.code.findbugs:annotations:3.0.1")
  implementation("joda-time:joda-time:2.12.7")
  implementation("com.google.guava:guava:33.2.0-jre")
  implementation("com.fasterxml.jackson.module:jackson-module-jsonSchema:2.17.0")
}

tasks.withType<Jar> {
}

description = "Yaml Grammar"
