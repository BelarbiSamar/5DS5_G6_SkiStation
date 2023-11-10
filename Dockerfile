FROM openjdk:11
EXPOSE 8089
ADD target/gestion-station-ski.jar samardevops.jar
ENTRYPOINT ["java","-jar","/samardevops.jar"]