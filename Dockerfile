FROM openjdk:8u131-jdk

ADD target/inventory-service.jar /app/dist/inventory-service.jar

EXPOSE 9004

VOLUME /tmp

ENTRYPOINT java -Dspring.profiles.active=$BOOTIFUL_MICRO_PIZZA_ENV -Djava.security.egd=file:/dev/./urandom -jar /app/dist/inventory-service.jar
