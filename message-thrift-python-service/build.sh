#!/usr/bin/env bash

docker build -t hub.zjh.com:8888/micro-service/message-service .

docker push hub.zjh.com:8888/micro-service/message-service:latest