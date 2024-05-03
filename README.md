DEPRECATION NOTICE: please note that this parser is now deprecated, please use [webapi-parser](https://github.com/raml-org/webapi-parser) instead.

—-

# RAML Java Parser
[![Gitter](https://badges.gitter.im/JoinChat.svg)](https://gitter.im/raml-org/raml-java-parser?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.raml/raml-parser-2/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.raml/raml-parser-2)

This is a Java implementation of a [RAML](http://raml.org) parser for versions [1.0](http://raml.org/raml-10-spec).
The parser depends on SnakeYaml, a Java YAML parser.

See http://raml.org for more information about RAML.


## Maven

```xml
  <dependency>
    <groupId>org.raml</groupId>
    <artifactId>raml-parser-2</artifactId>
    <version>${raml-parser-version}</version>
  </dependency>
```

### Development version

SNAPSHOT versions are NOT synchronized to Central. If you want to use a snapshot version you need to add the https://repository.mulesoft.org/nexus/content/repositories/snapshots/ repository to your pom.xml.

## Build

### JAR file without dependencies

```mvn clean package```

### JAR file with dependencies

```mvn clean package -P jar-with-dependencies```

**Run standalone validator**

```java -jar raml-parser-2-{version}.jar raml-file ...```

### Raml Java Parser JVM Arguments
In order to provide more flexibility, users can set different system properties when parsing different RAML files. Here we list all the system properties you can use right now:

Argument | Description | Default Value
-------- | ----------- | -------------
```yagi.json_duplicate_keys_detection``` | Setting it to true will make the parser fail if any JSON example contains duplicated keys | ```true```
```raml.json_schema.fail_on_warning``` | Setting it to true will make the parser fail if any example validated against a particular Json Schema throws a warning message | ```false```
```yagi.date_only_four_digits_year_length_validation```|	If TRUE, years of more than 4 digits are considered invalid | ```true```
```org.raml.date_only_four_digits_year_length_validation```|	Same as "yagi.date_only_four_digits_year_length_validation" (kept for backwards compatibility)| ```true```
```org.raml.dates_rfc3339_validation```|	if TRUE, enables RFC3339 validation for "datetime" type| ```true```
```org.raml.dates_rfc2616_validation```|	if TRUE, enables RFC2616 validation for "datetime" type| ```true```
```raml.xml.expandExternalEntities```|	Controls Java's EXTERNAL_GENERAL_ENTITIES_FEATURE and EXTERNAL_PARAMETER_ENTITIES_FEATURE| ```false```
```raml.xml.expandInternalEntities```|	Controls Java's DISALLOW_DOCTYPE_DECL_FEATURE| ```false```
```org.raml.strict_booleans```|	If FALSE, the strings "true" and "false" are valid for boolean type	| ```false```
```org.raml.fallback_datetime_to_datetime-only```|	if TRUE, value passed to a datetime type will fallback on the datetime-only type and validate accordingly| ```false```
```org.raml.cast_strings_as_numbers```|	if TRUE, will attempt to cast strings as numbers and validate| ```false```
```org.raml.nillable_types```|	if TRUE, makes all types equivalent to type: <code>type: type&#124; nil;</code> | ```false```
```raml.verifyRaml```|Verify the RAML file for YAML reference abuses | `true`
```raml.verifyReferenceCycle```|Specifically verify YAML reference cycles| `true`
```raml.maxDepth```|Limit depth of YAML references | `2000`
```raml.maxReferences```|Limit number of YAML references in expansions|`10000`
```raml.parser.encoding```|	Defines the charset being used by the parser| ```UTF-8```

The RAML parser's XML parsing components also respect Java XML entity properties.
 
## Usage

```java
RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(input);
if (ramlModelResult.hasErrors())
{
    for (ValidationResult validationResult : ramlModelResult.getValidationResults())
    {
        System.out.println(validationResult.getMessage());
    }
}
else
{
    Api api = ramlModelResult.getApiV10();

}
```

## Contribution guidelines

### Contributor’s Agreement

To contribute source code to this repository, please read our [contributor's agreement](http://www.mulesoft.org/legal/contributor-agreement.html), and then execute it by running this notebook and following the instructions: https://api-notebook.anypoint.mulesoft.com/notebooks/#380297ed0e474010ff43

### Pull requests are always welcome

We are always thrilled to receive pull requests, and do our best to process them as fast as possible. Not sure if that typo is worth a pull request? Do it! We will appreciate it.

If your pull request is not accepted on the first try, don't be discouraged! If there's a problem with the implementation, hopefully you received feedback on what to improve.

### Create issues...

Any significant improvement should be documented as [a GitHub issue](https://github.com/raml-org/raml-java-parser/issues) before anybody
starts working on it.

### ...but check for existing issues first!

Please take a moment to check that an issue doesn't already exist documenting your bug report or improvement proposal. If it does, it never hurts to thumb up the original post or add "I have this problem too". This will help prioritize the most common problems and requests.

### Merge approval

The maintainers will review your pull request and, if approved, will merge into the main repo. Commits get approval based on the conventions outlined in the previous section. For example, new features without additional tests will be not approved.
