# **photogram site 만들기**
***
## 프로젝트 개요 

* Environment   
   - Programming Language: Java, JSP, HTML, CSS, JavaScript    
   - Framework: Spring Framework 4.0, JPA Hibernate, Springboot   
   - DataBase: Mysql       
   - IDE & Tool: Eclipse STS, Mysql Workbench, Maven         
     
     
     
## 주요기능  
포토그램 기능을 만들어 보기  
외부에서 들어오는 것을 막고자 로그인한 사람만 게시글을 볼 수 있도록 Spring Security를 적용했습니다.   
ffmpeg 기능으로 동영상 썸네일 사진을 본인의 스토리에 올릴 수 있도록 했습니다. 
AWS EC2인스턴스로 우분투 리눅스를 사용해 배포했습니다.
Docker, Docker-compose를 사용하여 빌드하였습니다.
lets encrypt ssl인증서로 사이트를 https로 적용했습니다.
Jenkins로 깃허브와 연동하여 webhook으로 sts에서 깃허브로 커밋을 하면 젠킨스와 연결된 ssh로 빌드된 War파일을 전송할 수 있게 합니다.

## Docker, Docker-compose 설치
1.최신 패키지 리스트 업데이트
sudo apt update

2.도커 다운로드를 위해 필요한 https 관련 패키지 설치
sudo apt install apt-transport-https ca-certificates curl software-properties-common

3.도커 레포지토리 접근을 위한 gpg key설정
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

4.도커 레포지토리 등록
sudo add-apt-repository "deb [arch=amd64] http://download.docker.com/linux/ubuntu focal stable"

5.등록한 도커 레포지토리 까지 포함 최신 패키지 리스트 업데이트
sudo apt update 

6.도커 설치
sudo apt install docker-ce

7.도커 실행중임을 확인
sudo systemctl status docker 

sudo 명령 없이 도커 명령어 사용하기 설정
1.현 사용자(ubuntu) id를 도커 그룹에 포함
sudo usermod -aG docker ${USER}

2.터미널 끊고 다시 SSH로 터미널 접속)로그인 다시 하는것)

3.현 ID가 도커 그룹에 속해있는지 확인하는 명령어
id -nG

.도커 컴포즈 설치
sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

실행 권한 주기
sudo chmod +x /usr/local/bin/docker-compose

다음 명령 실행시 버전확인이 가능하면 성공
docker-compose --version

## Dockerfile을 생성
이름은 정확히 Dockerfile로 명시해야합니다.
```powershell
FROM ubuntu:20.04

RUN apt-get update 
RUN apt-get install -y openjdk-8-jdk-headless 
RUN apt-get install -y openjdk-8-jre-headless 
RUN apt-get -y upgradeRUN apt install ffmpeg -y --fix-missing
RUN mkdir upload-fileRUN mkdir jks-file
COPY ./myweb/*.war app.war
CMD ["java","-jar","/app.war"]
```

아래는 젠킨스 도커파일입니다.
```powershell
FROM jenkins/jenkins:lts

USER root
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y openssh-client
```


Docker-compose.yml파일을 작성합니다.
저는 database,springboot,nginx,certbot,Jenkins 로 정의된 5가지 컨테이너를 생성하였습니다.

mysql설정
```powershell
version: "3.0“
services:
  db:
    image:
    mysql:5.7
    environment:
      MYSQL_ALLOW_EMPTY_PASSWORD: "yes“
      MYSQL_USER: cos
      MYSQL_PASSWORD: cos1234
      MYSQL_DATABASE: blog
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
      - --lower_case_table_names=1
    volumes:
      - ./mysqldata:/var/lib/mysql
    ports:
      - "3306:3306“
    container_name: blog_mysql
```

springboot설정
```powershell
spring-boot:
  build: .
  volumes:
    - ./secure/blogram.site:/jks-file
  expose:
    - 8081
  restart: always
  container_name: spring-boot
  environment:
    - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/blog?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    - SPRING_DATASOURCE_USERNAME=cos
    - SPRING_DATASOURCE_PASSWORD=cos1234
    - SPRING_DATASOURCE_PLATFORM=org.hibernate.dialect.MySQL57Dialect

  links:
    - db
  depends_on:
    - db
```



 


 





