FROM openjdk:11
EXPOSE 8089
ADD target/5DS5-*.jar stationski.jar
ENTRYPOINT ["java","-jar","/stationski.jar"]