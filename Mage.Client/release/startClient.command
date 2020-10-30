#!/bin/sh

cd "`dirname "$0"`"

java -Xms256m -Xmx512m -Dfile.encoding=UTF-8 -jar ./lib/mage-client-${project.version}.jar &