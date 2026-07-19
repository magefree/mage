#!/bin/sh

cd "`dirname "$0"`"

java -Xmx2000m -jar ./lib/mage-client-${project.version}.jar &