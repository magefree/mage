#!/bin/sh

cd "`dirname "$0"`"

java -jar ./lib/Mage-Client-${project.version}.jar -Xms256M -Xmx1024M & 