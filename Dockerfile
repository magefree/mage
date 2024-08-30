# build xmage
# currently fails due to mismatching versions. how did this build ever work?

# FROM maven:3-jdk-8 AS builder

# COPY . .
# RUN ls -la  \
#  && mvn clean install -DskipTests \ 
#  && cd ./Mage.Client \
#  && ls -la \
#  && mvn package assembly:single \
#  && cd ./Mage.Server \
#  && ls -la \
#  && mvn package assembly:single \
#  && ls -la target \
#  && unzip target/mage-server.zip -d xmage-server

# instead of building, pull from current release
FROM curlimages/curl AS builder

WORKDIR /tmp

RUN curl https://github.com/magefree/mage/releases/download/xmage_1.4.53V1/mage-full_1.4.53-dev_2024-08-16_15-45.zip -L -o xmage.zip \
&& ls -la \
&& unzip -q xmage.zip -d xmage

FROM openjdk:8-jre

ENV XMAGE_DOCKER_SERVER_ADDRESS="0.0.0.0" \
    XMAGE_DOCKER_PORT="17171" \
    XMAGE_DOCKER_SEONDARY_BIND_PORT="17179" \
    XMAGE_DOCKER_MAX_SECONDS_IDLE="600" \
    XMAGE_DOCKER_AUTHENTICATION_ACTIVATED="false" \
    XMAGE_DOCKER_SERVER_NAME="mage-server"

EXPOSE 17171 17179
WORKDIR /xmage

# from being built
# COPY --from=builder /Utils/xmage-server .

# from release
COPY --from=builder tmp/xmage/xmage/mage-server /xmage/
COPY dockerContainerStart.sh /xmage/

RUN chmod +x \
    /xmage/startServer.sh \
    /xmage/dockerContainerStart.sh

CMD [ "./dockerContainerStart.sh" ]