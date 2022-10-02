FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/gipher-app-eureka-service-0.0.1-SNAPSHOT.jar /usr/src/gipher-app-eureka-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","gipher-app-eureka-service-0.0.1-SNAPSHOT.jar"]
