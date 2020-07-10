#!/bin/sh -x

script_dir="$(readlink -f -- "$BASH_SOURCE")"
script_dir="$(dirname -- $script_dir)"
cd $script_dir

# i dont really recommend using this script
# because both "child" scripts will output
# to the same terminal
# instead execute each individually

"${script_dir}/startClient.sh" &
"${script_dir}/startServer.sh" &
