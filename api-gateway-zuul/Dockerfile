#FROM java:8
FROM hub.zjh.com:8888/micro-service/java:8
MAINTAINER zjh

ADD ./target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]