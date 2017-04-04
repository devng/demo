#!/usr/bin/env bash

./gradlew clean build

docker run --rm --name sparkjava-demo -p 4567:4567 sparkjava-demo:v1
