FROM openjdk:8-jdk-alpine
COPY build/libs/molnibandi_masterwork-0.0.1-SNAPSHOT.jar moviedb.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "moviedb.jar"]
