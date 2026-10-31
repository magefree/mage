#!/bin/sh

cd "`dirname "$0"`"

java -Xmx2000m -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -jar ./lib/mage-client-${project.version}.jar &