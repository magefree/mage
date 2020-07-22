FROM openjdk:8

ENV DEBIAN_FRONTEND noninteractive
ENV MAVEN_OPTS '-Xmx2g'

COPY . .

RUN apt-get update \
  && apt-get install -y \
    maven \
    openjfx \
  && mvn clean install --define skipTests=true

ENTRYPOINT ["mvn"]
CMD ["test", "--define", "failIfNoTests=false", "--define", "test=EquipAbilityTest#testEquipShroud"]
