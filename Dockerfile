FROM maven:3.9.4-eclipse-temurin-17
VOLUME /tmp
COPY target/sale-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
