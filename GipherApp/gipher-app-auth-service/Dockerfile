FROM openjdk:11-jre-slim
WORKDIR usr/src
EXPOSE 8084
ENV MYSQL_URL jdbc:mysql://mysqlservice:3306/gipher?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
ADD ./target/gipher-app-auth-service-0.0.1-SNAPSHOT.jar /usr/src/gipher-app-auth-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","gipher-app-auth-service-0.0.1-SNAPSHOT.jar"]
