#!/usr/bin/env bash

# 先打包一层 images, 用于 pip install thrift 依赖
docker build -t python-base:latest -f Dockerfile.base .