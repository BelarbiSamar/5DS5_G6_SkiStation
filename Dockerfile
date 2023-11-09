FROM openjdk:8-jre-slim
EXPOSE 8089
WORKDIR /app
RUN apt-get update && apt-get install -y curl
RUN curl -o gestion-station-ski-1.0.jar -L "http://http://192.168.253.128/:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"
ENTRYPOINT ["java", "-jar", "gestion-station-ski-1.0.jar"]