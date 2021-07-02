FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/order-pickup-0.0.1-SNAPSHOT.jar order-pickup.jar
ENTRYPOINT ["java", "-jar", "order-pickup.jar"]