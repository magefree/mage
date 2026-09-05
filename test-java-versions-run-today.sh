#!/usr/bin/env bash
#
# EXAMPLE COMMAND TO RUN (copy as-is):
#   ./test-java-versions-run-today.sh "$(pwd)/tmp/java-builds/java26/bin/java" beta.xmage.today 17171 hmm 20
#   ./test-java-versions-run-today.sh "$(pwd)/tmp/java-builds/java26/bin/java" beta.xmage.today 17171 hmm 20 java8
#
# Point-check against a given server (default: beta.xmage.today), with/without
# add-opens, using a given JDK (default: tmp/java-builds/java26). Uses
# HeadlessConnectCheck (reconstructs the real UserData + userIdStr dumped
# from a live GUI client - see class javadoc).
#
# The optional 6th argument picks WHICH compiled client build to run:
#   (omitted) or "live" - use the repo's own current target/classes as-is
#   <label>             - use tmp/client-builds/<label>/ instead (a snapshot
#                          built earlier by test-java-versions-build-client.sh
#                          with a specific JDK's javac, e.g. "java8"/"java26")
# This lets you isolate "which JDK compiled the client code" as a variable,
# separately from "which JDK runs it" (the first argument).
#
# IMPORTANT: classpath explicitly lists every module's target/classes BEFORE
# the mvn-resolved dependency classpath. Without this, classes belonging to
# Mage/Mage.Common/etc (SessionImpl, UserData, ServerState...) get loaded from
# whatever stale mage-*.jar happens to be sitting in ~/.m2/repository instead
# of your freshly compiled/patched sources - this was silently masking the
# real connectSetUserData EOFException for a while.
#
# Prerequisites (after any source change to SessionImpl.java or anything
# reachable from HeadlessConnectCheck), when using the "live" client build:
#   mvn compile test-compile -pl Mage.Client -am -DskipTests
#
# Usage:
#   ./test-java-versions-run-today.sh [java_bin] [host] [port] [username] [timeoutSecs] [client_build_label]

set -uo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$REPO_ROOT"

JAVA_HOME_BIN="${1:-$REPO_ROOT/tmp/java-builds/java26/bin/java}"
HOST="${2:-beta.xmage.today}"
PORT="${3:-17171}"
USERNAME="${4:-hmm}"
TIMEOUT_SECS="${5:-20}"
CLIENT_BUILD="${6:-live}"

CP_FILE="$REPO_ROOT/matrix_results/client_classpath.txt"
if [ ! -s "$CP_FILE" ]; then
    echo "== Building client test classpath (one-time) =="
    mkdir -p "$REPO_ROOT/matrix_results"
    mvn -q dependency:build-classpath -pl Mage.Client -Dmdep.outputFile="$CP_FILE" -DincludeScope=test
fi

if [ "$CLIENT_BUILD" = "live" ]; then
    CLASS_ROOT="$REPO_ROOT"
else
    CLASS_ROOT="$REPO_ROOT/tmp/client-builds/$CLIENT_BUILD"
    if [ ! -d "$CLASS_ROOT" ]; then
        echo "FATAL: client build '$CLIENT_BUILD' not found at $CLASS_ROOT"
        echo "Build it first: ./test-java-versions-build-client.sh $CLIENT_BUILD <jdk_home>"
        exit 1
    fi
fi

echo "Using client build: $CLIENT_BUILD ($CLASS_ROOT)"

CP="$CLASS_ROOT/Mage.Client/target/classes:$CLASS_ROOT/Mage.Client/target/test-classes:$CLASS_ROOT/Mage/target/classes:$CLASS_ROOT/Mage.Common/target/classes:$CLASS_ROOT/Mage.Sets/target/classes:$CLASS_ROOT/Mage.Server.Plugins/Mage.Deck.Constructed/target/classes:$CLASS_ROOT/Mage.Plugins/Mage.Counter.Plugin/target/classes:$(cat "$CP_FILE")"

echo "=== without add-opens ==="
"$JAVA_HOME_BIN" -Djava.awt.headless=true -cp "$CP" \
  mage.client.game.HeadlessConnectCheck "$HOST" "$PORT" "$USERNAME" "$TIMEOUT_SECS"
echo "exit: $?"
echo

echo "=== with add-opens ==="
"$JAVA_HOME_BIN" -Djava.awt.headless=true --add-opens=java.base/java.io=ALL-UNNAMED -cp "$CP" \
  mage.client.game.HeadlessConnectCheck "$HOST" "$PORT" "$USERNAME" "$TIMEOUT_SECS"
echo "exit: $?"