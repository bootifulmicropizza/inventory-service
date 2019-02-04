FROM openjdk:8u191-jre-alpine3.8

EXPOSE 9004

VOLUME /tmp

ADD target/inventory-service.jar /app/dist/inventory-service.jar

ENTRYPOINT java -Dspring.profiles.active=$BOOTIFUL_MICRO_PIZZA_ENV -Djava.security.egd=file:/dev/./urandom -jar /app/dist/inventory-service.jar
