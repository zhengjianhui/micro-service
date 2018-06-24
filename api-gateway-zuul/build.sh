#!/usr/bin/env bash

mvn clean
mvn package

docker build -t hub.zjh.com:8888/micro-service/api-gateway-zuul .

docker push hub.zjh.com:8888/micro-service/api-gateway-zuul:latest
