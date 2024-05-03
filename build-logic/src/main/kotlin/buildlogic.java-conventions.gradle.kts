plugins {
  `java-library`
  `maven-publish`
}

repositories {
  mavenLocal()
  mavenCentral()
  maven {
    url = uri("https://maven.pkg.github.com/VEuPathDB/raml-for-jax-rs")
  }
}

group = "org.raml"
version = "1.0.51-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
  publications.create<MavenPublication>("maven") {
    from(components["java"])
  }
}

tasks.withType<JavaCompile>() {
  options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
  options.encoding = "UTF-8"
}
