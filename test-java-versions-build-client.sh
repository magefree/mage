#!/usr/bin/env bash
#
# EXAMPLE COMMAND TO RUN (copy as-is):
#   ./test-java-versions-build-client.sh java8 "$(pwd)/tmp/java-builds/java8"
#   ./test-java-versions-build-client.sh java26 "$(pwd)/tmp/java-builds/java26"
#   ./test-java-versions-build-client.sh java11fx "$(pwd)/tmp/java-builds/java11fx"
#
# Note on java11fx: on linux/aarch64, Liberica JDK 8 Full (bundled JavaFX)
# does not exist as a downloadable build (confirmed empirically against the
# BellSoft API - no matching release for version-feature=8). Liberica JDK 11
# Full is used instead as the closest available JavaFX-bundled JDK to
# approximate "a Java 8-era compiler with JavaFX" on this platform - see
# test-java-versions-prepare.sh for how it's fetched. This is an
# approximation, not a confirmed-identical substitute for a real Java 8 Full
# build.
#
# Builds Mage.Client and every module it depends on (Mage, Mage.Common,
# Mage.Sets, Mage.Server.Plugins/Mage.Deck.Constructed,
# Mage.Plugins/Mage.Counter.Plugin) using the given JDK, then snapshots the
# resulting target/classes (and Mage.Client's target/test-classes, where
# HeadlessConnectCheck lives) into tmp/client-builds/<label>/ so that build can be
# tested later without being overwritten by the next `mvn compile`.
#
# Third-party dependency jars (jboss-remoting, jboss-serialization, gson,
# protobuf, etc) are NOT rebuilt or copied here - they are pre-compiled
# binaries pulled from ~/.m2/repository at a version fixed by pom.xml, so
# they are identical regardless of which JDK compiles our own code. Only our
# own classes (UserData, SessionImpl, ServerState, ...) can actually change
# bytecode/structure depending on which javac built them - that's the
# variable this whole client-builds setup exists to isolate.
#
# Usage:
#   ./test-java-versions-build-client.sh <label> <jdk_home>
#
#   label     - name of the output folder under tmp/client-builds/ (e.g. java8, java26)
#   jdk_home  - JDK home used to run mvn for this build (its bin/java, bin/javac
#               etc are what actually compiles the code)

set -uo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$REPO_ROOT"

LABEL="${1:?Usage: $0 <label> <jdk_home>}"
JDK_HOME="${2:?Usage: $0 <label> <jdk_home>}"

if [ ! -x "$JDK_HOME/bin/javac" ]; then
    echo "FATAL: $JDK_HOME/bin/javac not found or not executable - is this a JDK (not just a JRE)?"
    exit 1
fi

OUT_DIR="$REPO_ROOT/tmp/client-builds/$LABEL"

echo "== Building with JAVA_HOME=$JDK_HOME =="
"$JDK_HOME/bin/java" -version

JAVA_HOME="$JDK_HOME" PATH="$JDK_HOME/bin:$PATH" \
    mvn clean compile test-compile -pl Mage.Client -am -DskipTests
MVN_EXIT=$?

if [ "$MVN_EXIT" -ne 0 ]; then
    echo
    echo "FATAL: mvn build failed (exit $MVN_EXIT) - see the error output above."
    echo "Fix the build errors manually (e.g. pom.xml adjustments needed for this"
    echo "specific JDK) and re-run this script. Refusing to snapshot classes from"
    echo "a failed/partial build."
    exit "$MVN_EXIT"
fi

echo "== Snapshotting compiled classes into $OUT_DIR =="
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

MODULE_CLASS_DIRS=(
    "Mage.Client/target/classes"
    "Mage.Client/target/test-classes"
    "Mage/target/classes"
    "Mage.Common/target/classes"
    "Mage.Sets/target/classes"
    "Mage.Server.Plugins/Mage.Deck.Constructed/target/classes"
    "Mage.Plugins/Mage.Counter.Plugin/target/classes"
)

for dir in "${MODULE_CLASS_DIRS[@]}"; do
    if [ -d "$dir" ]; then
        dest="$OUT_DIR/$dir"
        mkdir -p "$dest"
        cp -a "$dir/." "$dest/"
        echo "  copied $dir -> $dest"
    else
        echo "  WARNING: $dir not found, skipped"
    fi
done

echo
echo "Done. tmp/client-builds/$LABEL is ready."
echo "Pass it to test-java-versions-run-today.sh as the --client-build argument."