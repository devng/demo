## Example of a Spark Java Application working with Guice

This application uses the [Spark](http://sparkjava.com/) micro Java 8 framework together with [Guice](https://github.com/google/guice) for creating a simple _Hello World_ web application.

### Regiments
- JDK 8
- Docker

### How to run

```
./gradlew jettyRun
```

Or by using Docker:
```
./build-and-run.sh
```

Open `http://localhost:8080` in your browser.
