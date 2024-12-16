FROM openjdk:17-jdk-slim

COPY target/pt-java-dux-software-0.0.1-SNAPSHOT.jar pt-java-dux-software.jar

COPY .env .env

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/pt-java-dux-software.jar"]
