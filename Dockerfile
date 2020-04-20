FROM maven:3.6.3 AS build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN mvn install


FROM openjdk:8-alpine
EXPOSE 8081

RUN mkdir /app

COPY --from=build /usr/src/app/target/london-people-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
