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

도커 컴포즈 설치  
sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

실행 권한 주기  
sudo chmod +x /usr/local/bin/docker-compose

다음 명령 실행시 버전확인이 가능하면 성공  
docker-compose --version

## Dockerfile, docker-compose.yml파일을 생성
이름은 정확히 Dockerfile로 명시해야합니다.  
우분투환경과 젠킨스환경의 2개의 도커파일을 만들었습니다.
첫번째 도커파일에서 ffmpeg설치를 위해 우분투 환경으로 설정했습니다.  
Docker-compose.yml파일을 작성합니다.  
저는 database, webserver, nginx, certbot, Jenkins로 정의된 5가지 컨테이너를 생성하였습니다.  
Dockerfile과 docker-compose.yml 파일은 커밋하여 올려놨습니다. 

## letsencrypt 인증서 발급 및 적용
아래는 cerbot에서 ssl인증서를 발급받기위해 docker-compose.yml파일 에서의 cerbot컨테이너 설정입니다.  
```powershell
certbot:
    depends_on:
      - webserver
    image: certbot/certbot
    container_name: certbot
    volumes:
      - ./certbot-etc:/etc/letsencrypt
      - ./myweb:/usr/share/nginx/html
    command: certonly --webroot --webroot-path=/usr/share/nginx/html --email dbtjdvy2422@gmail.com --agree-tos --no-eff-email --keep-until-expiring -d blogram.site -d www.blogram.site
```  
발급받은 인증서의 pem파일을 jks파일로 변경해준 뒤 적용해줍니다.
```powershell
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out cert_and_key.p12 -name ttp -CAfile chain.pem -caname root

keytool -importkeystore -deststorepass (설정할 비밀번호) -destkeypass (설정할 비밀번호) -destkeystore letsencrypt.jks -srckeystore cert_and_key.p12 -srcstoretype PKCS12 -srcstorepass (비밀번호)

keytool -import -trustcacerts -alias root -file chain.pem -keystore letsencrypt.jks
```
springboot에서의 yml설정과 nginx.conf설정만 해주면 ssl이 적용되고 https를 사용할 수 있습니다.  

## Jenkins webhook을 사용해 깃허브와 ssh를 연동하여 war파일 전송
Jenkins로 깃허브와 연동하여 webhook으로 sts에서 깃허브로 커밋을 하면 젠킨스와 연결된 ssh로 빌드된 War파일을 전송할 수 있게 하였습니다.  
Dokcerfile과 docker-compse.yml파일에서 jenkins환경설정을 해주고 jenknins를 들어가 아래와 같이 내부설정을 해주었습니다.  

![image](https://user-images.githubusercontent.com/62457271/138330368-aab03ff9-2db4-4d6f-916a-e32715e60890.png)


 





