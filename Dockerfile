FROM adoptopenjdk/openjdk11:alpine-jre
RUN addgroup -S billing && adduser -S billing -G billing
USER billing:billing
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]