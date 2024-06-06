#!/bin/sh

cd "`dirname "$0"`"

java  -Xmx1024m -XX:MaxPermSize=384m -jar ./lib/mage-server-${project.version}.jar
