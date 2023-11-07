FROM openjdk:8
EXPOSE 8089
ADD target/gestion-station-ski-1.0.jar ski-docker.jar
ENTRYPOINT ["java","-jar","ski-docker.jar"]