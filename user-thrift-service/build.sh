#!/usr/bin/env bash

mvn clean
mvn package

docker build -t hub.zjh.com:8888/micro-service/user-service:latest .

docker push hub.zjh.com:8888/micro-service/user-service:latest
