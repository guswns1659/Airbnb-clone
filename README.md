![GitHub issues](https://img.shields.io/github/issues/guswns1659/Airbnb-clone) ![GitHub closed issues](https://img.shields.io/github/issues-closed/guswns1659/Airbnb-clone) ![coverage](https://img.shields.io/badge/coverage-89%25-brightgreen)

# Airbnb-clone 

에어비앤비의 숙박 조회, 예약, 예약 취소 등 일부 기능을 클론한 프로젝트 
- 클코드를 지향하며 코드 리뷰 문화를 통한 기능 통합을 진행.
- TDD를 적용하며 커버리지 70% 이상 달성. 
- OOP를 기반으로 도메인 설계 및 객체에 적절한 역할과 책임을 부여. 

# 데모 영상 및 서비스 사진 

- [데모영상](https://youtu.be/iLq8LginsKU)

<details markdown="1">
<summary>서비스 사진</summary>

- 메인 화면
<img src="https://user-images.githubusercontent.com/55608425/92931726-daac4100-f47e-11ea-81de-5f7e7b483ee2.png" width=400>

- 검색 결과 화면
<img src="https://user-images.githubusercontent.com/55608425/92931717-d849e700-f47e-11ea-8940-6f71ad78bc69.png" width=400> 

- 예약 화면
<img src="https://user-images.githubusercontent.com/55608425/92931732-dd0e9b00-f47e-11ea-9933-b81a5dbd9b7d.png" width=400>

- 예약 내역 화면
<img src="https://user-images.githubusercontent.com/55608425/92931729-dc760480-f47e-11ea-8472-7ee3e972922b.png" width=400>

</details>

## 프로젝트 지향점 

OOP(객체지향프로그래밍), TDD(테스트주도개발), 클린코드 

### OOP 

- 도메인 설계 및 엔티티 분석 

![image](https://user-images.githubusercontent.com/55608425/91532838-dc87e780-e949-11ea-9abb-1daf93b34ced.png)

- Account 설계 : Account은 멤버와 호스트로 나눠진다. 호스트는 멤버의 기능을 그대로 가져가고 숙소 등록하는 기능이 추가된다. 
- Accommodation 설계 : Accommodation은 Account와 다대다 관계라서 일대다, 다대일 관계로 매핑하고 중간 엔티티를 Reservation으로 생성한다. 
- Picture 설계 : Accommodation은 여러개의 Picture를 갖는다.


### TDD

- 기존 프로젝트 대비 코드 커버리지 40% 이상 향상

**Before**

![image](https://user-images.githubusercontent.com/55608425/92323985-3e49ff00-f078-11ea-97ff-f278bda2779a.png)

**After**

![image](https://user-images.githubusercontent.com/55608425/92382290-53da2a00-f147-11ea-8746-edd08f4a18a7.png)

### 클린코드

- [P.R를 활용한 코드 리뷰 문화](https://github.com/guswns1659/Airbnb-clone/pull/13)

![image](https://user-images.githubusercontent.com/55608425/92382629-de228e00-f147-11ea-8647-1f699adce831.png)


## 코딩 가이드라인 

- 기존 프론트엔드 개발자와 협업했던 프로젝트가 당시에 정했던 코딩 가이드라인

### 에어비앤비 - 7팀
- FE - [Taek](https://github.com/seungdeng17)⚛️, [Huey](https://github.com/hu2y) 🏄‍♂️
- BE - [Jack](https://github.com/guswns1659) :elephant:

### 팀 그라운드 룰
- 오전 11시 스크럼 `google hangout`을 사용 
- wiki에 각자 당일 스크럼 기록
    - 컨디션, 하루 목표 등등 다양하게!
    - 전날 구현하면서 느낀 아쉬움과 오늘은 어떻게 행동할건지 적어보기. 

### Issue 관리
- [클래스명] Issue 제목
```
[BE] 배포
[React] 버튼컴포넌트 구현
[Vue] 모달창 구현
```

### PR 관리
- [클래스명 #Issue번호] PR 제목
- Auto Close를 사용할 필요가 있는 경우 PR에 Close Keyword를 적어서 Issue Close가 가능합니다.

```
[BE #1] DB 설계
```


### 공유사항
 - git ignore는 각자 작성
 - BE가 구현한 API에 대해서는 Spring rest Docs로 작성해 URL 공유
 - API URL는 백엔드가 정하고 데이터 형식은 다같이


## 브랜치 정보

### git branch
- master: 배포용 브랜치
- dev: 개발 브랜치(default branch)
- deploy : 배포 연습용 브랜치
- 작업을 시작할 때: 자신의 클래스 개발 브랜치에서 feature-<클래스>/issue-번호 으로 브랜치 생성
    ex) feature-iOS/issue-32
- 배포 주기 : 수, 금 17:00

### commit message
| 타입 | 설명 |
|--|--|
|Feat|새로운 기능 추가|
|Fix|버그 수정|
|Docs|문서 수정|
|Refactor|코드 리팩토링|
|Style|코드 포맷팅 (코드 변경이 없는 경우)|
|Test|테스트 코드 작성|
|Chore|소스 코드를 건들지 않는 작업(빌드 업무 수정)|

```
 [#43] Feat: boilerPlate
```
 - 이슈 단위로 개발한다.
 - 작업을 완료되면, 작업하던 브랜치에서 개발 브랜치(dev)로 Pull Request를 생성한다. jack은 로컬에서 merge.
 - 머지를 완료했으면 기능(feature)브랜치는 github과 local git에 모두 삭제한다. 
 - dev에서 master로 pull request 할 때 BE 나 FE 코드에 충돌나는 경우, 코드를 작성한 팀원에게 알린다.
브랜치 
