# account-book

## account-book
가계부 서버 개인 프로젝트 서비스이면서 account_book_app(앱 클라이언트)및 account_book_admin_web(웹 클라이언트)과 통신하기 위한 백엔드 포트폴리오입니다.

<br>
● 제작기간 : 2022.05.11~2023.06.19(33일)(1인 프로젝트)

### 개발 환경
> 1. Java 11<br/>
> 2. Spring Boot 2.7.11<br/>
> 3. AWS EC2(ubuntu)

### IDE
> 1. Intellij<br/>

## API 소개
### 1. User API
|Url|Http Method|기능|Parameter
|:---|:---:|:---:|:---:|
|/api/user|POST|회원가입|○email(String)<br/> ○password(String)<br/> ○name(String)<br/> ○tel(String)
|/api/user/login|POST|로그인|○email(String)<br/> ○password(String)
|/api/user/auth|GET|로그인 여부|○token(String)
|/api/user/logout|POST|로그아웃|○userId(String)
|/api/user/social-login/kakao|POST|카카오 로그인|○id(String)<br/> ○oauthToken(String)
|/api/user/social-login/exist|POST|소셜 로그인 계정 존재여부|○email(String)<br/> ○name(String)<br/> ○socialType(String)
|/api/user/{id}|GET|프로필 상세보기|-
|/api/user/{id}|PATCH|프로필 수정|○profileUrl(String)<br/> ○name(String)<br/> ○tel(String)<br/>
|/api/user/{id}|DELETE|회원 탈퇴 또는 회원 삭제|-
<br/>

### 2. Admin API
|Url|Http Method|기능|Parameter
|:---|:---:|:---:|:---:|
|/api/user|GET|회원 목록(페이징 처리 및 검색)| ○name(String)<br/> ○email(String)<br/> ○role(String)<br/> ○socialType(string)<br/> ○page(number)<br/> ○size(number) 
|/api/user/{id}|GET|프로필 상세보기|-
|/api/user/{id}|PATCH|프로필 수정|○profileUrl(String)<br/> ○email(String)<br/> ○name(String)<br/> ○tel(String)<br/> ○role(String)<br/>
<br/>


