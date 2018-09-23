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

# Turning to the tutorial

The tutorial specifies the dependencies; those go into the `<dependencies>` section of our `pom.xml`.

For example, the tutorial specifies this dependency, along with two others:

```
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
  <version>2.3.0</version>
</dependency>
```			     

However, rather than just blindly copying and pasting each of these, it's a good idea to see if there is a later version that might have bug fixes, compatibility with later versions of Java, etc.

So, we look each of these up by going a web search on the artifactId along with Maven Central, e.g.:

* <https://www.google.com/search?q=jackson-dataformat-yaml+maven+central>

That leads us to this site:

* <https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml/2.9.6>

And we see that for the latest version, the `<dependency>` element for Maven is this:

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml -->
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
  <version>2.9.6</version>
</dependency>
```

Copying the comment with the URL along with the dependency isn't required, but it is handy as a reference
in case we want to go back later and see if there is a newer version.  So, I suggest always copying it along with the `<dependency>` element.

# Test yaml file

The next thing in the tutorial is an example yaml file:

```yaml
# Details of a user
---
name: Test User
age: 30
address:
  line1: My Address Line 1
  line2: Address line 2
  city: Washington D.C.
  zip: 20000
roles:
  - User
  - Editor
```

We'll create a directory called `testdata` and put the file `user1.yaml` in that directory.

# The Java Bean

The tutorial then refers to a `User.java` "Bean".

A "Bean" is just a "plain old Java class" that follows a certain set of conventions.  The most important of these are:
* a no-argument constructor
* getters and setters for each property that follow the standard naming convention.

The "Bean" convention is used frequently in Java programming to make it easy for various tools to use *reflection* to automatically discover things about your code and allow "magic" to happen.   

We see that the `User.java` file already specifies a package, namely `com.mms.mja.blog.demo.yaml`.  So,
we'll create that directory under `src/main/java`

```
mkdir -p src/main/java/com/mms/mja/blog/demo/yaml
```

Then, using either emacs or vim, we can edit that file and paste in the code from the tutorial:

```
emacs src/main/java/com/mms/mja/blog/demo/yaml/User.java
```

The next file is `YamlTesting.java` which we can put into the same directory, since it's in the same package:

```
emacs src/main/java/com/mms/mja/blog/demo/yaml/YamlTesting.java
```

With this file in place, we could either replace the property

```
    <userDefinedMainClass>${project.groupId}.yaml.Demo01</userDefinedMainClass>
```

With this, i.e. the fully specified name of the class containing the `main` from the demo,
which would allow us to run the file with `mvn exec:java`

```
    <userDefinedMainClass>com.mms.mja.blog.demo.yaml.YamlTesting</userDefinedMainClass>
```

Note, though, that this code refers to `user.yaml`, which we stored in a directory
called `testdata` under the filename `user1.yaml`.  So, we'll need to modify line 16 of the code
to read:

```
    User user = mapper.readValue(new File("testdata/user1.yaml"), User.class);
```

Or, we can leave our main class as it was, so that we can try writing our own yaml demo, and
instead run the original demo code by running `mvn package` to create a jar file with all of the
dependencies, and specifying the class path and fully qualified class name
like this:

```
mvn package
java -cp target/yaml-Demo01-1.0-jar-with-dependencies.jar com.mms.mja.blog.demo.yaml.YamlTesting
```

That looks like this, matching the output in the tutorial:

```
$ java -cp target/yaml-Demo01-1.0-jar-with-dependencies.jar com.mms.mja.blog.demo.yaml.YamlTesting
com.mms.mja.blog.demo.yaml.User@1e127982[
  name=Test User
  age=30
  address={line1=My Address Line 1, line2=Address line 2, city=Washington D.C., zip=20000}
  roles={User,Editor}
]
$ 
```

# What next?

So, what we might do next is see if we can apply this to another problem.

For example, suppose we want to be able to store information about Student objects (with name, perm, majors)
in a Yaml file, and then load that file into an ArrayList<Student>.

The first step is to create a Student.java class with the appopriate getters and setters.

We put this in `src/main/java/edu/ucsb/cs56/pconrad/yaml/Student.java`

We also add tests for this in `src/test/java/edu/ucsb/cs56/pconrad/yaml/StudentTest.java`

