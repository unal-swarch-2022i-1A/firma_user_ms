FROM openjdk:8-jre-alpine
LABEL org.opencontainers.image.source https://github.com/unal-swarch-2022i-1A/firma_user_ms
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
