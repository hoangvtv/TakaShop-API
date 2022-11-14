# TakaShop-API

## Steps to Setup
## 1. Clone the repository
git clone https://github.com/hoangvtv/TakaShop-API.git

## 2. Configure MySQL database

Create a MySQL database named file_demo, and change the username and password in src/main/resources/application.properties as per your MySQL installation - 

spring.datasource.url= jdbc:mysql://localhost:3306/<YOUR_PROJECT>?useSSL=false

spring.datasource.username= <YOUR MYSQL USERNAME>
  
spring.datasource.password= <YOUR MYSQL PASSWORD>

## 3. Run the app using maven

cd spring-boot-file-upload-download-rest-api-example
mvn spring-boot:run
That's it! The application can be accessed at http://localhost:8081.

You may also package the application in the form of a jar and then run the jar file like so -

mvn clean package
java -jar target/file-demo-0.0.1-SNAPSHOT.jar
