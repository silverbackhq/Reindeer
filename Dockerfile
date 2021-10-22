# To build
# $ docker build -t clivern/reindeer:1.1.3 .
#
# To Run
# $ docker run -d \
#    --env APP_PORT=8000 \
#    --env DB_CONNECTION=h2 \
#    --env DB_HOST=127.0.0.1 \
#    --env DB_PORT=3306 \
#    --env DB_DATABASE=/app/storage/db \
#    --env DB_USERNAME=root \
#    --env DB_PASSWORD=secret \
#    --name=reindeer \
#    --publish 8000:8000 \
#    clivern/reindeer:1.1.3

FROM gradle:7.2.0-jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew build --info

FROM openjdk:11.0.13-jre-slim

RUN mkdir -p /app/releases
RUN mkdir -p /app/configs
RUN mkdir -p /app/storage

VOLUME /app/storage

ENV REINDEER_LOAD_FROM=system
ENV APP_PORT=8000
ENV DB_DATABASE=/app/storage/db

COPY --from=builder /home/gradle/src/build/libs/reindeer-1.1.3-fat.jar /app/releases/reindeer-1.1.3-fat.jar
COPY --from=builder /home/gradle/src/.env.example /app/configs/.env

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "/app/releases/reindeer-1.1.3-fat.jar", "--env=/app/configs/.env"]