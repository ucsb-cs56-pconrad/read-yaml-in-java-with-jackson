# read-yaml-in-java-with-jackson

Code from https://dzone.com/articles/read-yaml-in-java-with-jackson


# How this repo was created

I started with the tutorial above, but I had to use some knowledge
about how to work with Maven in order to turn the tutorial into a
working repo.

In this part of the README.md, I'm going to try to document what I had
to do, and what knowledge was needed as I go along.

# Borrow and adapt a pom.xml

First, I borrowed and adapted a `pom.xml` from another project.

Here is, step-by-step, the parts of the `pom.xml` that I adapted.

First, the first few lines of the `pom.xml` need no adjustment; these
are pretty standard for any project:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apa\
che.org/maven-v4_0_0.xsd">

  <!-- model version - always 4.0.0 for Maven 2.x POMs -->
    <modelVersion>4.0.0</modelVersion>

```

Now we get to the "project coordinates" and we have to do some editing:

```xml
    <!-- project coordinates - values which uniquely identify this project -->

  <groupId>edu.ucsb.cs56.pconrad</groupId>
  <artifactId>yaml-Demo01</artifactId>
  <version>1.0</version>
  <url>https://ucsb-cs56-pconrad.github.io/read-yaml-in-java-with-jackson</url>
```

I chose `edu.ucsb.cs56.pconrad` for my group coordinates since that
is a reasonably good unique identifier for the work I do for CS56 at UCSB.

I'll use the same string for my package name.

If you putting together your own repo that's going to be public, you should
choose your own identifier.  (Perhaps `edu.ucsb.cgaucho` if `cgaucho` is your UCSB username, i.e. your umail address without the @umail.ucsb.edu part).

My artifact id is something that is used to identify the project for Maven.  This is allowed to have hyphens in it, though note that java package names
cannot have hyphens.   I chose `yaml-Demo01`.

The URL is where the github pages page corresponding to the repo will end up
being published, i.e.

* Repo: <https://github.com/ucsb-cs56-pconrad/read-yaml-in-java-with-jackson>
* Github Pages: <>

Then, in the next section, we have:

```xml
 <!-- configure what mvn package produces -->

  <packaging>jar</packaging>
  <name>${project.artifactId}</name>
```

As long as the `<name>` value is taken from the `${project.artifactId}`, then
we don't have to update this at all.   That helps us keep the `pom.xml` file
DRY (i.e. we are following the advice: "don't repeat yourself".)  We reduce
the number of things that have to be updated when we copy a pom.xml from
one project to another.

Next, we have the properties:

```xml
<!-- what version of Java should we use and what encoding for files? -->
<properties>	   
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>1.8</maven.compiler.source>
  <maven.compiler.target>1.8</maven.compiler.target>
  <userDefinedMainClass>${project.groupId}.yaml.Demo01</userDefinedMainClass>
</properties>
```

Here we see that we've chosen `edu.ucsb.cs56.pconrad.yaml` as the package
name that our `Demo01` class containing our main will live in.  (The edu.ucsb.cs56.pconrad comes from the definition earlier of `<groupId>`).

Therefore we now need to create a boilerplate main, along with a directory for it to live in:

```
mkdir -p src/main/java/edu/ucsb/pconrad/cs56/yaml
```

Then, in the directory `src/main/java/edu/ucsb/pconrad/cs56/yaml/` we
can put a placeholder for the main class:

```
package edu.ucsb.cs56.pconrad.yaml;
public class Demo01 {
  public static void main(String [] args) {
    System.out.println("Yaml Demo01");
  }
}
```

We can test our work so far by running:

```
mvn compile
mvn exec:java
```

We should see (with lots of surrounding output deleted):

```
...
[INFO]
[INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ yaml-Demo01 ---
Yaml Demo01
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
...
```

# Changes to .gitignore

It's a good idea to add these lines into the .gitignore provided for java.   The comments
(lines starting with `#`) explain the purpose:


```
# emacs

*~
\#*\#

# vim

*.swp

# Maven

target/
```

