#!/bin/sh -x

script_dir="$(readlink -f -- "$BASH_SOURCE")"
script_dir="$(dirname -- $script_dir)"
cd $script_dir

java -Xms256m -Xmx2g -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -jar "${script_dir}/lib/mage-client-1.4.44.jar"

