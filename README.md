# README #

This README would normally document whatever steps are necessary to get your application up and running.

### zooplus home work ###

* Quick summary
* Version 1.1

### Setup ###

* Install Git
* Install Java
* Install Gradle (Optional)
* run app from IDE or from command line

### Clone ###
To get started you can simply clone this repository using git:
```
git clone https://github.com/Galjente/zooplus.git
cd zooplus
```

### Build an executable JAR
You can run the application from the command line using:
```
gradle bootRun
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
gradle clean build
```
Then you can run the JAR file with:
```
java -jar build/libs/zooplus-*.jar
```

*Instead of `gradle` you can also use the gradle-wrapper `./gradlew` to ensure you have everything necessary to run the Gradle build.*