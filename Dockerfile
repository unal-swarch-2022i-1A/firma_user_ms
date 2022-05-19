FROM openjdk:11-jre-slim
COPY firma_user_ms-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
COPY /config/application.properties /usr/local/lib/config/
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
