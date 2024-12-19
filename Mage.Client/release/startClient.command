#!/bin/sh

cd "`dirname "$0"`"

java -Xmx2000m -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -jar ./lib/mage-client-${project.version}.jar &