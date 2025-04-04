FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/BenderWeatherBot-0.0.1-SNAPSHOT.jar /app/bender.jar
ENTRYPOINT ["java", "-jar", "bender.jar"]
