FROM openjdk:11
WORKDIR /app
COPY ./target/*.jar /app/stationSki.jar
CMD ["java", "-jar", "/app/stationSki.jar"]