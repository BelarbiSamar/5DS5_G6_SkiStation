FROM openjdk:11
EXPOSE 8089
ADD target/gestion-station-ski.jar stationski.jar
ENTRYPOINT ["java","-jar","/stationski.jar"]