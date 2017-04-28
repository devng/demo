## Example Spark Java Application

This application uses the [Spark](http://sparkjava.com/) micro Java 8 framework for creating a simple RESTful web application.

It also shows the usage of different templating engines such as [Thymeleaf](http://www.thymeleaf.org/) and [MVEL](https://github.com/mvel/mvel).

### Regiments
- JDK 8
- Docker

### How to run

```
./gradlew build run
```

Or by using Docker:
```
./build-and-run.sh
```

Open `http://localhost:4567` in your browser. Available endpoints:
- `/hello`
- `/rest/users`
- `/users`
