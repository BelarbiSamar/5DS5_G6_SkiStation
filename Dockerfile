FROM openjdk:8-jdk
WORKDIR /app
COPY ./target/*.jar /app/khaddem.jar
CMD ["java", "-jar", "/app/khaddem.jar"]