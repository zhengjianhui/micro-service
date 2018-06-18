#!/usr/bin/env bash

mvn clean
mvn package

docker build -t api-gateway-zuul .
