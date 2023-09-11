FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
