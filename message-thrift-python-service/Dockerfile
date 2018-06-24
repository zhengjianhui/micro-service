FROM hub.zjh.com:8888/micro-service/python-base:latest
MAINTAINER zjh

# 增加环境变量
ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python", "/message/message_service.py"]