FROM openjdk:11-jre-slim-buster
EXPOSE 8050
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
