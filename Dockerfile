FROM openjdk:11-jdk
WORKDIR /app
COPY target/users-api-0.0.1-SNAPSHOT.jar /app/users-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "users-api.jar"]