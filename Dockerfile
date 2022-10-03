FROM gradle:7.5-jdk17-alpine as compile
COPY . /home/source/java
WORKDIR /home/source/java
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build -x test

FROM openjdk:17-alpine
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/delivery-service-0.0.1-SNAPSHOT.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/application/java/delivery-service-0.0.1-SNAPSHOT.jar"]