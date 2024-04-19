FROM maven:3-openjdk-17 as maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package


FROM openjdk:17
WORKDIR /app
COPY --from=maven /app/target/helpnearby-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","/app/helpnearby-0.0.1-SNAPSHOT.jar"]