FROM openjdk:8
EXPOSE 8089
ADD target/gestion-station-ski-0.0.1-SNAPSHOT.jar ski-docker.jar
ENTRYPOINT ["java","-jar","ski-docker.jar"]