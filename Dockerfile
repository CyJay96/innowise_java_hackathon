FROM openjdk:17-jdk-slim

LABEL maintainer="cyjay96"

EXPOSE 8080

WORKDIR /app

COPY ./build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
