FROM maven:3.8.3-openjdk-11 AS build

WORKDIR /server

COPY src /server/src

COPY pom.xml /server/

RUN mvn -f /server/pom.xml clean package

CMD ["java","-jar","/server/target/user.result.board-0.0.1-SNAPSHOT.jar"]