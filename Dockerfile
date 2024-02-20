FROM openjdk:17-jdk
ADD /build/libs/micheck12.jar mycheck12.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mycheck12.jar"]