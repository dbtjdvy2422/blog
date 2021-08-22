FROM openjdk:8-jdk-alpine

RUN mkdir upload-file
RUN mkdir jks-file
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war

CMD ["java","-jar","/app.war"]