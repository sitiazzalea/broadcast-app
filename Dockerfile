FROM eclipse-temurin:8-jre-jammy

WORKDIR /app

COPY target/broadcast-1.0.0.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]