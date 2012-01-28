#!/bin/sh

cd "`dirname "$0"`"

java -jar ./lib/mage-client-${project.version}.jar -Xms256M -Xmx1024M & 