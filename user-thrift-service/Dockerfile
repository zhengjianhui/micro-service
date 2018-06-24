FROM hub.zjh.com:8888/micro-service/java:8
MAINTAINER zjh

ADD ./target/user-thrift-service-1.0-SNAPSHOT.jar /user-service.jar

ENTRYPOINT ["java", "-jar", "/user-service.jar"]