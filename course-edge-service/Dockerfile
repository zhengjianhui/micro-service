FROM hub.zjh.com:8888/micro-service/java:8
MAINTAINER zjh

ADD ./target/course-edge-service-1.0-SNAPSHOT.jar /course-edge-service.jar

ENTRYPOINT ["java", "-jar", "/course-edge-service.jar"]