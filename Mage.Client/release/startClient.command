#!/bin/sh

cd "`dirname "$0"`"

java -Xmx1024m -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -jar ./lib/mage-client-${project.version}.jar &