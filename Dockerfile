FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/beer-catalog-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} beer-catalog.jar
ENTRYPOINT ["java","-jar","/beer-catalog.jar"]