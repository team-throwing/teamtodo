# teamtodo

- ./db.properties.example 파일 참고 필수
- ./database/teamtodo-create-table.sql : DDL

CheckList
- [ ] Database 생성
- [ ] MySql 접속용 계정 생성, 생성한 Database에 권한 설정
- [ ] Copy db.properties.example :  cp db.properties.example src/main/resources/db.properties
> cp A B : A의 파일을 B의 경로의 파일명으로 복사
- [ ] src/main/resources/db.properties 확인 후 내용 변경
- [ ] `.gitignore` 설정 확인

TODO
- [ ] Make init data Script : `database/init-data.sql`


###  주의사항
- *.properties 파일은 .gitignore에도 등록되어있듯, 업로드 금지
- 환경변수를 사용하는 .env 파일도 사용할 경우 업로드 금지
- 예시 파일을 올리고자 할 경우, .env.example 형식으로 값은 변경해서
- .idea/workspace.xml 업로드 금지