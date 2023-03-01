# INSTAGRAME SERVER TEAM (쩡이 & 올리브)


# Server 13기 Instagram_IOS Clone Project

## ※ ios 기반 인스타그램 클론 프로젝트로, 실제 화면 및 기능과 유사하게 작동하는 인스타그램 클론 앱이다.
---
## ◆ 개발 목표
서버 13기 instagram_ios팀에서는 ERD설계 및 서버구축부터 시작하여, 스프링 부트를 이용하여 인스타그램의 여러 기능들을 실제 구현해보고 rest api의 규칙에 맞게 ios 클라이언트들과 협동하여 프로젝트를 진행해보며 개발 프로세스의 전반적인 방법론과 개발법을 익히는데 주안을 둔다.

---
### 팀 계획서 [바로가기](https://www.notion.so/softsquared/A-iOS-6648f3ead7aa4ea0b07c3e792ffe4a65)
---

### Instagram API 기본 명세 [바로가기](https://docs.google.com/spreadsheets/d/1UxmF8Kh5oQa_UejQxl2WeycWs-lclE10d-Fw0Vt4P9Q/edit#gid=0)

---
### ERD 설계(AQueryTool.com)
<img style="margin:10px" width="970" alt="erd설계사진" src="https://user-images.githubusercontent.com/72601276/215269988-8a61a7e7-24a6-4f60-99de-2aaf77ab5e9d.png">  

  
1. [DB설계 바로가기](https://aquerytool.com/aquerymain/index/?rurl=a861bb5c-e3cb-4ab5-b04d-6bd697d2028c)  
2. password : 732eir


---
## ◆ 구현 기능
1. 회원가입
2. 일반+Oauth2로그인(JWT인증구현)
3. 피드 업로드,수정,조회 기능
4. 마이 피드페이지 상세 정보 (구독자수,팔로워수,게시물 수 등)
4. 스토리 업로드,수정,조회 기능
6. 팔로잉,팔로워 리스트 기능
7. 댓글 및 대댓글
8. 팔로워
9. 좋아요
10. 디엠
---
## ◆ 개발 과정
### - 개발 기간
#### - 2023년 01월 28일(토) ~ 2023년 02월 10일(금) / 총 2주
#### - ERD 설계 및 환경 구축,api리스트 작성 3일 / 개발 11일
---
## ◆ API 설계 리스트
  
#### ◇  쩡이 담당

##### | method | URI | description | 
POST	/app/users	유저 생성 API (일반 회원가입)</br>
POST	/app/users/login	유저 로그인 (일반 로그인 +jwt)</br>
POST	/oauth2/authorization/facebook	유저 로그인 (페이스북 로그인+jwt) </br>
PATCH	/app/users/:userId	유저 정보 수정</br>
GET	/app/users/:userId	유저 개인 피드 조회(필요시 별도구현예정)</br>
POST	/app/posts/multifiles	유저 피드 업로드 (+multifile upload)</br>
PATCH	/app/posts/:postId	유저 피드 수정</br>
DELETE	/app/posts/:postId	유저 피드 삭제</br>
POST	/app/follows/:userId	유저 팔로우 토글</br>
POST	/app/follows/users/:userId	유저 팔로우</br>
POST	/app/unfollows/users/:userId	유저 언팔로우</br>
POST	/app/likes/:postId	게시글 좋아요</br>
POST	/app/unlikes/:postId	게시글 좋아요 취소</br>
GET	/app/users/block-states/:userId	유저 차단 상태 조회</br>
POST	/app/users/blocks/:userId	유저 차단</br>
POST	/app/users/unblocks/:userId	유저 차단 해제</br>
GET	/app/users/profiles/:pageuserId	유저 개인 피드 페이지 (+ 프로필정보)</br>
GET	/app/users/following-posts	유저 팔로우 게시물 조회(메인화면)</br>
GET	/app/posts/popular	좋아요순 게시물 조회(돋보기탭 랜덤 피드)</br>
GET	/app/users/:userId/following-lists	유저 팔로잉 목록 리스트</br>
GET	/app/users/:userId/follower-lists	유저 팔로우 목록 리스트</br>
POST	/app/users/:pageuserId/profile-images	마이페이지 프로필 업로드</br>
GET	/app/follow-states/users/:userId	유저 팔로우 상태 조회</br>
GET	/app/users/like-states/:postId	유저 좋아요 상태 조회</br>
PATCH	/app/users/:userId/emails	유저 이메일 별도 수정 API</br>
PATCH	/app/users/:userId/phones	유저 전화번호 별도 수정 API</br>
POST	/app/users/:userId/profiles	유저 프로필 업로드 API</br>
POST	/app/posts/:postId/days	게시물 최종 업데이트 날짜 조회</br>
POST	/app/feeds	유저 피드 업로드(게시물 한개)</br>
GET	/app/posts/:postId/day-details	포스트 날짜 상세 정보 가져오기(분전,시간전,그제...)</br>
POST	/app/posts/:postId	포스트 업로드(다중파일)</br>
GET	/app/shops/products	쇼핑 화면 썸네일 조회</br>
POST	/app/shops/products	쇼핑 화면 썸네일 등록(클라이언트 개발용)</br>
GET	/app/posts/popular/:postId	랜덤 피드(돋보기) 게시물 조회</br>
GET	/app/posts/:postId/all-comments	포스트 댓글 개수 조회</br>
POST	/app/messages	메시지(DM) 전송</br>
GET	/app/messages/:userId	메시지(DM) 조회</br>
DELETE	/app/messages/:messageId	메시지(DM) 삭제</br>

---- 
! _각 API는 restAPI 규칙을 준수했으며,API별 Response는 각각 클라이언트의 요청을 따랐음._
## 개발 환경
+ Sever
    + Spec: t2.micro
    + Ip: 3.38.228.156 (탄력적IP)
    + Url: https://prod.imklera-risingcamp.shop
    + Id	ubuntu
    + Password	(pem)
    + OS	ubuntu20.04
    + WebServer	nginx1.10.3
    + Backend-Language	php7.0.25
+ Git Url
    + https://github.com/mock-rc13/instagram_server_olive_jjungie				
+ Storage
    + arn:aws:s3:::instagram-server13
+ Path
    + nginx	/etc/nginx/	웹 서버 설정 파일 경로	
	+ /var/www/html/	소스파일 위치 경로	
	+ service nginx start|restart|status	Command	
    + mysql	/etc/mysql	DB 설정 파일 경로	
	+ service mysql start|restart|status	Command	
	
---   
## ◆  예외 처리
- Spring Validation 을 통한 유효성 검증
- Custom ValidationException/APIException handling 설계
- Controller단에서 jwt토큰검증 및 형식 예외처리
--- 
## ◆   java API
- JPA 모델 Entity 맵핑
- JDBC 사용
---  
## ◆   기능 개발
1. 회원가입 및 로그인 인증
    ![Untitled](https://user-images.githubusercontent.com/72601276/217947286-52d87b6e-46e3-4934-89fd-1e6086a02bc9.png)
    + 회원가입시 name,email,password,phone,userName을 입력
    + 틀리면 해당하는 에러메시지가 뜬다. (ex_'중복된이메일입니다','전화번호를 입력해주세요')
    + 로그인은 userName,email,전화번호 를 통해 모두 가능하다. (단, 이들값은 모두 고유값이어야 함)
    + <img width="444" alt="1" src="https://user-images.githubusercontent.com/72601276/217948175-eb7a3d26-50fa-4cb2-8b12-a9edcab0743e.png">
    + 일반로그인,Oauth2로그인 시 응답으로 Jwt token을 반환한다.
    + **X-ACCESS-TOKEN** jwt 헤더값을 통해 인증을 구현하였다.

2. 유저 정보 수정
    + <img src="https://user-images.githubusercontent.com/72601276/217952956-ad8ca837-c1ad-47ff-a257-275f10aeb90f.jpg"  style="width:300px"/>
    + 개인정보를 부분적으로 수정 가능하다.
    + ___JWT 토큰인증___

---	
	
<div display="flex" justify-content="space-between">
<img width="300" src="https://user-images.githubusercontent.com/72601276/217989346-7eaa88d7-9723-43fb-bfc1-882f0ae0f761.jpg"/>
<img width="353" alt="이미지 썸네" src="https://user-images.githubusercontent.com/72601276/217988807-83249f7f-104a-4180-8ba3-10f860e02bfb.png">	
	</div>	
	
 <div display="flex" justify-content="space-between">
	<img src="https://user-images.githubusercontent.com/72601276/216826421-ce2f519b-3102-4b30-abd7-91bc7368cec0.jpg" width="250px"/>  
	<img width="500px" alt="팔로잉유저게시물들" src="https://user-images.githubusercontent.com/72601276/216826565-cc385ff4-0330-4869-a684-628eff85fab7.png">
	</div>	
---	
	
3. 피드
    + 피드 업로드시, 글내용과 파이어베이스에서 String화된 이미지의 리스트를 Post한다.
    + 피드 수정시, 글 내용만 가능하다. 
    + 피드 삭제시, postId를 주소값에 넣으면 인증 후 삭제한다.
    + 피드 메인 화면은 유저가 팔로우한 유저들의 피드만 게시한다.
    + 유저 개인 페이지에는 팔로수,게시글수,로그인한 유저의 기준으로 해당 페이지 유저를 팔로했는지,해당 페이지 유저가 로그인한 유저와 같은지를 표시한다.
    + 메인 피드의 경우 로그인 한 유저의 좋아요 상태 및 댓글 갯수와 시간 등을 반영하였다.
    + ___JWT 토큰인증___

4. 유저 페이지 팔로잉/팔로워 조회 리스트	
    + <div display="flex" justify-content="space-between">
	<img width="300" src="https://user-images.githubusercontent.com/72601276/217914661-046938bf-2ec9-4bc3-a1e7-f800d5b0d0fb.jpg"/>
	<img width="300" alt="팔로잉리스트으으으" src="https://user-images.githubusercontent.com/72601276/217007252-8b81b074-fcf7-4367-b631-cbc410f95e01.png">  
	</div>
		
    + 유저 페이지에서 각 팔로워/팔로잉 수를 클릭했을때 해당 페이지 유저의 구독 상태를 반영한다.	
    + followState는 로그인 한 유저가 리스트의 유저(즉,조회하는 페이지유저의 팔로우 유저)를 표시한다.(이는  클라이언트 팔로우/팔로워 버튼 상태 표시를 위함이다.)	
    + equalUserState는 로그인한 유저가 페이지 유저의 팔로워,팔로잉 유저인지 상태를 표시한다.(자기 자신은 구독 리스트에 뜨지않아야 하기 때문이다.)	

4. 그 외 좋아요 / 차단 기능
    + 유저 팔로우,유저 차단,게시물 좋아요는 각각 등록,등록 취소 API가 있다.
    + 각각 상태 조회 API를 통해 로그인유저의 타 유저에 대한 팔로우 상태등을 조회 가능하다.

5. 그 외 기능 구현
    + 인기 피드(돋보기)는 모든 포스팅을 인기 순(좋아요 순)으로 정렬한다.
    + 	+ <img width="865" alt="조조아아아" src="https://user-images.githubusercontent.com/72601276/217360266-d0aabc74-b300-4b70-95aa-8a3645b936c8.png">	
	+ <img width="350" alt="조아요순" src="https://user-images.githubusercontent.com/72601276/217360512-ac28da09-b5bd-4107-9e01-38f4de37b1cd.png">	
	+ <img width="404" alt="조조조2" src="https://user-images.githubusercontent.com/72601276/217360836-352fa222-22ee-4d16-9aab-98669db44a36.png">	
    + 인기 피드(돋보기)를 클릭할 시 해당 포스팅 화면으로 넘어갈 수 있다.
    + 쇼핑(샵)은 상품 썸네일을 등록하고 조회 할 수 있다.
    + 디엠은 전송,조회,삭제가 가능하다.

---


<div align="center">
	<img src="https://img.shields.io/badge/Spring Boot-white?style=flat&logo=Spring Boot&logoColor=#6DB33F" />
	<img src="https://img.shields.io/badge/Spring Security-E34F26?style=flat&logo=Spring Security&logoColor=#6DB33F" />
	<img src="https://img.shields.io/badge/Spring-white?style=flat&logo=Spring&logoColor=#6DB33F" />
</div>


