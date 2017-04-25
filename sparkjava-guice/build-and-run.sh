#!/usr/bin/env bash

./gradlew clean build

docker run --rm --name sparkjava-guice-demo -p 8080:8080 sparkjava-guice-demo:v1
