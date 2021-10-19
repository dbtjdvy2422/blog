FROM ubuntu:20.04

RUN apt-get update 
RUN apt-get install -y openjdk-8-jdk-headless 
RUN apt-get -y upgrade
RUN apt install ffmpeg -y --fix-missing
RUN mkdir upload-file
RUN mkdir jks-file

COPY ./myweb/*.war app.war

CMD ["java","-jar","/app.war"]
