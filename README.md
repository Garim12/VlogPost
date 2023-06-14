# 내배캠 과제 블로그

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
    수정 = Body       삭제 = param
    
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
  수정의 경우 body는 자동으로 객체를 생성해줘서 운영에 좋아보이고 삭제의 경우 Param은 자동으로 객체를 생성하기보단 각 변수별로 데이터를 비워줄 수 있으므로 좋다고 생각함

3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
  지금같은 간단한 블로그의 경우 CRUD가 Data 테이블을 id,title,author,password,content,date만 있어 설계하기 쉬운거같음

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    Controller,Repository 두개만 적용함

5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
