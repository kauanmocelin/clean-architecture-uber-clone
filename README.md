# Uber Clone
Clean Architecture with Quarkus

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/kauanmocelin/uber-clone/blob/main/LICENSE)

# About

This is an implementation of Clean Architecture with quarkus as objective to learn more about software architecture by hands on.
With this architecture all layers was tested independently and easily, an elegance way to accomplish uncoupling of code.

# Architectural Diagram

![Hexagonal Architecture Diagram](clean-architecture.jpg)

# Built With

- Java 17
- [Quarkus](https://quarkus.io/)
- Unit/Integration Testing
- Maven

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _uber-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _uber-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _uber-jar_, is now runnable using `java -jar target/*-runner.jar`.