## Example Spark Java Application

This application uses the [Spark](http://sparkjava.com/) micro Java 8 framework for creating a simple RESTful web application.

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

Open `http://localhost:4567` in your browser. Available REST endpoints
- `/hello`
- `/rest/users`
- `/users`
