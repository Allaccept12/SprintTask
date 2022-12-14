# SprintTask program

## runbook
- 전제조건 (java11, docker 설치)

## 컴파일 방법 
- 프로젝트 루트 경로에서 `docker-compose up` 실행 (백그라운드로 실행하려면 `docker-compose up -d`)
: runs the application(spring-boot port:8080) and its db (mysql port: 3306)

- DB 접속은 `docker exec -it sprint_database bash` 접속

## API
1. 저자 등록 - POST : '/api/author'
2. 책 등록 - POST : '/api/book'
3. 저자 조회 - GET : '/api/author'
4. 책 전체 조회 - GET : '/api/book'

## Swagger ui
http://localhost:8080/swagger-ui/index.html

## “왜” 이렇게 작성하였는지 :

1.  책의 저자들이 여러명일수 있다는 조건에 대응해서
    mysql은 컬렉션 저장이 안되기 때문에 별도의 테이블을 만들어 값타입 컬렉션으로 저장했습니다. 하지만 값타입 컬렉션의 문제점으로 컬렉션에 변경사항이 생기면 예상치 못한(주인 엔티티와 연관된 모든 데이터 삭제 -> 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장 하는 등 ) 쿼리가 발생하기 때문에 변경 소지가 적고 매우 단순한 데이터에 사용해야한다는 제약사항이 있음을 알고있습니다.
    -> 그러나 책의 저자는 변경될 소지가 적다는 생각을 했고, 그래서 값 타입 컬렉션으로 관계설정을 진행했습니다..
    -> 만약 변경될 소지가 많다면 현재 저자와 책의 관계는 가정을 하자면 ManyToMany이기 때문에 중간에 테이블을 두고
    book <- ManyToOne 단방향(중간테이블)  -> author 로 구성 할 것같다는생각을 했습니다.

2. 패키지 구조
   각각의 도메인(자기만의 repository를 가진)을 기준으로 패키지를 구성했습니다. 이렇게 작성을 하니 관련된 코드들이 응집해있는 장점이 있다고 생각했고, 또 클래스의 import문으로 클래스의 역할을 유추하기 쉽다는 장점이 있다고 생각했습니다.크게 제 프로젝트는 인프라는 없지만 글로벌패키지(공통적인 객체), 인프라스트럭처패키지(이메일 알림, sms알림 등 외부서비스), 도메인패키지(도메인 각자 레이어를 가짐)을 가집니다. 도메인은 저 나름의 에그리거트를 기준으로  모아서 패키지를 구성했습니다.

3. AuthorNotFoundExcetion이 application패키지에 같이 있는 이유
    이번 프로젝트에서 ExceptionClass를 따로 빼서 관리하지 않은 이유는 두 가지가 있는데 첫 번째는 Exception을 현재 사용지가 AuthorService에서 밖에 쓰지 않기때문에 따로 관리할 필요성을 느끼지 못했고 확장 된다면 충분히 제어 가능하다고 생각했습니다. 두번쨰는 같은 패키지에 둠으로서 import문을 작성하지 않아도 되는 장점이 있어서 보다 깔끔하게 class파일을 관리할 수 있어서 입니다.
    만약 ExceptionClass가 늘어난다면 따로 해당 도메인 패키지에 Exception패키지를 구성해서 한곳에서 관리하는게 용이하다고 생각하지만 현재는 많이 작고, 늘어난다해도 제어가능하다고 생각하였습니다. 


