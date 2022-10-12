FROM openjdk:11 as build
MAINTAINER berkay22demirel.com
COPY target/ReadingIsGood-0.0.1-SNAPSHOT.jar reading-is-good.jar
ENTRYPOINT ["java","-jar","/reading-is-good.jar"]