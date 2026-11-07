#!/bin/sh

cd "`dirname "$0"`"

java -Xmx1024m -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -jar ./lib/mage-server-${project.version}.jar
