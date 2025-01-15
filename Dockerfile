FROM docker.io/maven:3.9.9-eclipse-temurin-23-alpine as MAVEN_BUILD
COPY ./ ./
RUN --mount=type=cache,target=/root/.m2 mvn clean package
RUN mv target/*.jar target/simplenotes.jar

FROM docker.io/eclipse-temurin:23-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /target/simplenotes.jar /app
EXPOSE 8080
CMD ["java", "-jar", "simplenotes.jar"]