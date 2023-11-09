FROM openjdk:11
ARG NEXUS_URL=http://192.168.33.10:8081/#browse/browse:maven-releases:com%2Fexample%2FstationSki%2F1.1.0%2FstationSki-1.1.0.jar
ARG JAR_FILE=stationSki-1.1.0.jar
ADD ${NEXUS_URL}${JAR_FILE} stationski-1.1.0.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","/stationSki-1.1.0.jar"]


