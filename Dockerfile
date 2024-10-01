FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven

COPY . /app
WORKDIR /app

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/your-app-name.jar"]