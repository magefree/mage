#!/bin/sh

cd "`dirname "$0"`"

java -Xms256M -Xmx512M -Dfile.encoding=UTF-8 -Djava.security.policy=./config/security.policy -jar ./lib/mage-server-${project.version}.jar
