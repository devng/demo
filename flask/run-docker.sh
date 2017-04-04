#!/bin/bash

docker build -t flask-demo:v1 .

docker run --rm --name flask-demo -p 5000:5000 flask-demo:v1
