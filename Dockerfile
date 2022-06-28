FROM maven:3.8.6-openjdk-11-slim
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
WORKDIR /usr/src/app/
RUN mvn package

FROM openjdk:8-jre-alpine
LABEL org.opencontainers.image.source https://github.com/unal-swarch-2022i-1A/firma_user_ms
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
