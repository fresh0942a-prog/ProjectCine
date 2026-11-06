#Etapa 1: compilacion
from  maven:3.9-eclipse-temurin-17 as build
workdir /app
copy . .
run mvn -f pom.xml clean package -DskipTests

#etapa 2: creacion de la imagen final
from  eclipse-temurin:17-jre
workdir /app
copy --from=build /app/target/*.jar ./app.jar
expose 8090
entrypoint ["java","-XX:MaxRAMPercentage=75","-jar","app.jar"]