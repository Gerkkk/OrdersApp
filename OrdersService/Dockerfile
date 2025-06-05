FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY --chown=gradle:gradle . .
RUN apt-get update && apt-get install -y netcat
RUN ./gradlew generateProto
RUN ./gradlew build -x test --no-daemon
RUN gradle dependencies --refresh-dependencies && \
    gradle build -x test --no-daemon

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080 9090
CMD ["java", "-jar", "app.jar"]