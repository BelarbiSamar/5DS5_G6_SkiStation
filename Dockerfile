FROM openjdk:11
EXPOSE 8089
ADD target/gestion-station-ski.jar StationSki.jar
ENTRYPOINT ["java","-jar","/StationSki.jar"]