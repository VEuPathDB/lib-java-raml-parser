plugins {
  id("buildlogic.java-conventions")
}

dependencies {
  implementation(project(":yagi"))
  implementation("com.github.java-json-tools:json-schema-validator:2.2.14")
  implementation("org.mozilla:rhino:1.7.12")
  implementation("com.googlecode.juniversalchardet:juniversalchardet:1.0.3")
  implementation("org.apache.ws.xmlschema:xmlschema-core:2.2.1")
  implementation("javax.json:javax.json-api:1.0")
  implementation("org.glassfish:javax.json:1.0.4")
  implementation("com.sun.mail:jakarta.mail:1.6.3")
  implementation("commons-io:commons-io:2.16.1")
  implementation("org.slf4j:slf4j-api:1.7.36")

  testImplementation("junit:junit:4.12")
  testImplementation("org.mockito:mockito-core:1.9.5")
  testImplementation("org.hamcrest:hamcrest-all:1.3")
  testImplementation("org.slf4j:slf4j-simple:1.7.36")
  testImplementation("com.flipkart.zjsonpatch:zjsonpatch:0.3.4")
  testImplementation("com.google.code.gson:gson:1.7.2")
  testImplementation("xmlunit:xmlunit:1.6")
}

description = "Raml Java Parser 2nd generation"
