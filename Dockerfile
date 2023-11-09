FROM openjdk:8-jdk
WORKDIR /app
COPY ./target/*.jar /app/stationSki.jar
CMD ["java", "-jar", "/app/stationSki.jar"]