FROM openjdk:11

ENV DEBIAN_FRONTEND noninteractive
ENV MAVEN_OPTS '-Xmx2g'

RUN apt-get update \
  && apt-get install -y \
    maven

COPY . .

RUN mvn clean install --define skipTests=true

ENTRYPOINT ["mvn"]
CMD ["test", "--define", "failIfNoTests=false"]
