
#Etapa 1: compilacion
from  maven:3.8.5-openjdk-17 as build
workdir /app
copy . .
run mvn -f pom.xml clean package -DskipTests

#etaoa 2: creacion de la imagen final
from  openjdk:17.0.1-jdk-slim
workdir /app
copy --from=build /app/target/*.jar ./app.jar
expose 80
entrypoint ["java","-jar","app.jar"]
