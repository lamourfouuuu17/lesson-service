FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/lesson-service-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "server.jar"]