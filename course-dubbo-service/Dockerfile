FROM hub.zjh.com:8888/micro-service/java:8
MAINTAINER zjh

ADD ./target/course-dubbo-service-1.0-SNAPSHOT.jar /course-dubbo-service.jar

ENTRYPOINT ["java", "-jar", "/course-dubbo-service.jar"]