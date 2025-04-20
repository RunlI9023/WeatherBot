FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/WeatherBot-0.0.1-SNAPSHOT.jar /app/wbot.jar
ENTRYPOINT ["java", "-jar", "wbot.jar"]
