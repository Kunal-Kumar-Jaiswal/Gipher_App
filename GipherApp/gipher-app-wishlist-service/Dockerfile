FROM openjdk:11-jre-slim
WORKDIR usr/src
ENV MONGO_URI mongodb://mongodbservice:27017/gipherdb
ENV MONGO_DB_NAME gipherdb
ADD ./target/gipher-app-wishlist-service-0.0.1-SNAPSHOT.jar /usr/src/gipher-app-wishlist-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","gipher-app-wishlist-service-0.0.1-SNAPSHOT.jar"]
