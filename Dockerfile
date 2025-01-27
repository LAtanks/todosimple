FROM gradle:jdk17-alpine AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle build --no-daemon -x test

FROM openjdk:17-slim
ENV PROJECT_HOME /usr/src/todosimple
ENV JAR_NAME todosimple.jar

WORKDIR $PROJECT_HOME


COPY --from=build /app/build/libs/todosimple.jar $PROJECT_HOME/$JAR_NAME
ENTRYPOINT ["java", "-jar", "todosimple.jar"]