## 📘 **Article Management** 📘

Java Spring Boot backend API of system for managing articles and statistics with profile register, login and role authorization
- Java 17, Maven, Spring Boot, Spring Security, JWT, Hibernate, MapStruct, Lombok, PostgreSQL
- Crud operations for all entities (Users - authors / admins, Articles, Statistics) 
- Layered architecture (Repository - Service - Controller) 

## :computer: **How to run the project**
1. Download [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and [Maven](https://maven.apache.org/install.html)
2. Open your terminal (cmd) and check Java installation by `java -version` and Maven `mvn -version`
3. Clone repository: Open your terminal (cmd) and use `https://github.com/MishaHMK/ArticlesBackend.git`
4. Download and install [PostgreSql](https://www.postgresql.org/download/).
5. Open your terminal (cmd) and create PostgreSQL user `psql -U User`
6. Create a database `CREATE DATABASE DB_NAME;`
7. In src/main/recources/application.properties put proper PostgreSQL db data:
   <img width="851" height="275" alt="image" src="https://github.com/user-attachments/assets/4502b958-5e1f-46c9-8ce6-412ddd118593" />
8. Сompile project `mvn clean install` 
9. Run project `java -jar target/ArticleApp-0.0.1-SNAPSHOT.jar`

## Test account creds: 
  Admin email: george.price@example.com
  
  Author email: olivia.baker@example.com
  
  Password for both: securePassword123

## Test how project works: [![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/team66-9067/articleappdemo/collection/18049779-2f923fd3-d71e-4e43-808c-eca54d4d8ef0) 

You can use interactive Swagger Documentation
<img width="1344" height="906" alt="image" src="https://github.com/user-attachments/assets/49a578f6-d9c8-432e-87d5-472fdd07d901" />



