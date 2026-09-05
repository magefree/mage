#!/usr/bin/env bash
#
# EXAMPLE COMMAND TO RUN (copy as-is):
#   ./test-java-versions-run.sh "$(pwd)/tmp/java-builds/java8" "$(pwd)/tmp/java-builds/java26" tmp/mage-builds/java8/mage-server
#
# XMage client/server Java-version + add-opens compatibility matrix runner.
#
# Uses mage.client.game.HeadlessConnectCheck (no Swing UI, no JavaFX, no
# display needed) instead of the full GUI client, so no xvfb is required.
#
# Prerequisites (one-time, from the repo root):
#   1. Place HeadlessConnectCheck.java at:
#        Mage.Client/src/test/java/mage/client/game/HeadlessConnectCheck.java
#   2. tmp/java-builds/java8 and tmp/java-builds/java26 present (JDK homes)
#   3. mvn compile test-compile -pl Mage.Client -am -DskipTests
#
# Usage:
#   ./test-java-versions-run.sh <java8_home> <java26_home> <server_dir>
#
#   server_dir must contain lib/mage-server-*.jar (a built distribution, e.g.
#   tmp/mage-builds/java8/mage-server or tmp/mage-builds/java26/mage-server -
#   pick whichever server build you want to compare against this run). The
#   client side runs straight from this repo's own
#   Mage.Client/target/{classes,test-classes} - no separate client_dir needed.
#
# Example commands for comparing different server builds:
#   ./test-java-versions-run.sh "$(pwd)/tmp/java-builds/java8" "$(pwd)/tmp/java-builds/java26" tmp/mage-builds/java8/mage-server
#   ./test-java-versions-run.sh "$(pwd)/tmp/java-builds/java8" "$(pwd)/tmp/java-builds/java26" tmp/mage-builds/java26/mage-server

set -uo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

JAVA8_HOME="$1"
JAVA26_HOME="$2"
SERVER_DIR="$3"

SERVER_JAR=$(realpath "$SERVER_DIR"/lib/mage-server-*.jar 2>/dev/null | head -1)
if [ -z "$SERVER_JAR" ]; then
    echo "FATAL: no mage-server-*.jar found under $SERVER_DIR/lib/"
    exit 1
fi

RESULTS_DIR="$REPO_ROOT/matrix_results"
mkdir -p "$RESULTS_DIR"
SUMMARY="$RESULTS_DIR/summary.tsv"
echo -e "server_java\tserver_opens\tclient_java\tclient_opens\tresult\tdetail" > "$SUMMARY"

ADD_OPENS="--add-opens=java.base/java.io=ALL-UNNAMED"
CONNECT_TIMEOUT_SECS=20
SERVER_BOOT_WAIT_SECS=40  # max seconds to wait for "Started MAGE server" (card DB loading can be slow)
CONNECT_HOST="127.0.0.1"
CONNECT_PORT="17171"
CONNECT_USER="matrixtest"

# --- build the client-side classpath ONCE (mvn dependency:build-classpath is slow) ---
echo "== Building client test classpath (one-time) =="
CP_FILE="$RESULTS_DIR/client_classpath.txt"
( cd "$REPO_ROOT" && mvn -q dependency:build-classpath -pl Mage.Client \
    -Dmdep.outputFile="$CP_FILE" -DincludeScope=test )
if [ ! -s "$CP_FILE" ]; then
    echo "FATAL: failed to build client classpath, check 'mvn dependency:build-classpath' output above"
    exit 1
fi
CLIENT_TEST_CLASSPATH="$REPO_ROOT/Mage.Client/target/classes:$REPO_ROOT/Mage.Client/target/test-classes:$REPO_ROOT/Mage/target/classes:$REPO_ROOT/Mage.Common/target/classes:$REPO_ROOT/Mage.Sets/target/classes:$REPO_ROOT/Mage.Server.Plugins/Mage.Deck.Constructed/target/classes:$REPO_ROOT/Mage.Plugins/Mage.Counter.Plugin/target/classes:$(cat "$CP_FILE")"

kill_server() {
    local pid="$1"
    kill "$pid" 2>/dev/null   # SIGTERM first, in case it shuts down cleanly

    local dead_wait=0
    while [ "$dead_wait" -lt 5 ]; do
        if ! kill -0 "$pid" 2>/dev/null; then
            break  # process is gone
        fi
        sleep 1
        dead_wait=$((dead_wait + 1))
    done

    if kill -0 "$pid" 2>/dev/null; then
        echo "  (server pid $pid ignored SIGTERM after 5s, sending SIGKILL)"
        kill -9 "$pid" 2>/dev/null
    fi

    wait "$pid" 2>/dev/null
    wait_port_free
}

print_emoji_matrix() {
    echo "=== MATRIX ==="
    echo "✅ OK   🖥️❌ server fail   🔌❌ client rejected (protocol/version)   💥 broken JDK   ⏱️ timeout   ➖ skipped   ❓ other"
    echo

    declare -A CELL
    declare -a SERVER_CFGS=()
    declare -a CLIENT_CFGS=()

    while IFS=$'\t' read -r s_java s_opens c_java c_opens result detail; do
        [ "$s_java" = "server_java" ] && continue  # skip header row

        local server_cfg="${s_java}/${s_opens%%(*}"
        local client_cfg="${c_java}/${c_opens%%(*}"

        if [[ ! " ${SERVER_CFGS[*]-} " == *" $server_cfg "* ]]; then
            SERVER_CFGS+=("$server_cfg")
        fi
        if [[ ! " ${CLIENT_CFGS[*]-} " == *" $client_cfg "* ]]; then
            CLIENT_CFGS+=("$client_cfg")
        fi

        local emoji
        case "$result" in
            OK)      emoji="✅" ;;
            SKIPPED) emoji="➖" ;;
            TIMEOUT) emoji="⏱️" ;;
            FAIL)    emoji="🔌❌" ;;
            SERVER_FAILED_TO_START)
                if [[ "$detail" == *"Exec format error"* ]]; then emoji="💥"; else emoji="🖥️❌"; fi
                ;;
            UNKNOWN)
                if [[ "$detail" == *"Exec format error"* ]]; then emoji="💥"; else emoji="❓"; fi
                ;;
            *) emoji="❓" ;;
        esac
        CELL["$server_cfg|$client_cfg"]="$emoji"
    done < "$SUMMARY"

    printf "%-18s" "server \\ client"
    for c in "${CLIENT_CFGS[@]}"; do printf "| %-16s" "$c"; done
    echo
    for s in "${SERVER_CFGS[@]}"; do
        printf "%-18s" "$s"
        for c in "${CLIENT_CFGS[@]}"; do
            printf "| %-16s" "${CELL["$s|$c"]:-❓}"
        done
        echo
    done
    echo
}

wait_port_free() {
    local port_wait=0
    while [ "$port_wait" -lt 10 ]; do
        if ! (exec 3<>/dev/tcp/127.0.0.1/"$CONNECT_PORT") 2>/dev/null; then
            return  # connection refused = port is free
        fi
        exec 3<&- 3>&- 2>/dev/null
        sleep 1
        port_wait=$((port_wait + 1))
    done
}

run_one() {
    local server_java_label="$1" server_java_home="$2" server_opens="$3"
    local client_java_label="$4" client_java_home="$5" client_opens="$6"

    local server_opens_label client_opens_label
    server_opens_label=$( [ "$server_opens" = "1" ] && echo opens || echo noopens )
    client_opens_label=$( [ "$client_opens" = "1" ] && echo opens || echo noopens )
    local tag="srv-${server_java_label}-${server_opens_label}_cli-${client_java_label}-${client_opens_label}"
    local server_log="$RESULTS_DIR/${tag}.server.log"
    local client_log="$RESULTS_DIR/${tag}.client.log"

    echo "=== $tag ==="

    # add-opens is a Java 9+ VM flag - skip nonsensical Java 8 + opens combos
    if { [ "$server_java_label" = "java8" ] && [ "$server_opens" = "1" ]; } || \
       { [ "$client_java_label" = "java8" ] && [ "$client_opens" = "1" ]; }; then
        echo -e "${server_java_label}\t${server_opens_label}(N/A)\t${client_java_label}\t${client_opens_label}(N/A)\tSKIPPED\tadd-opens not valid on Java 8" >> "$SUMMARY"
        return
    fi

    # --- start server ---
    local server_args=("-Xmx1024m")
    [ "$server_opens" = "1" ] && server_args=("$ADD_OPENS" "${server_args[@]}")
    ( cd "$SERVER_DIR" && exec "$server_java_home/bin/java" "${server_args[@]}" -jar "$SERVER_JAR" ) > "$server_log" 2>&1 &
    local server_pid=$!

    # poll for real readiness instead of a single fixed sleep - card DB loading
    # time varies a lot (cold cache, machine load, JDK startup speed differ)
    local waited=0
    local server_started=0
    while [ "$waited" -lt "$SERVER_BOOT_WAIT_SECS" ]; do
        if grep -q "Started MAGE server" "$server_log" 2>/dev/null; then
            server_started=1
            break
        fi
        if ! kill -0 "$server_pid" 2>/dev/null; then
            # process already died - no point waiting further
            break
        fi
        sleep 1
        waited=$((waited + 1))
    done

    if [ "$server_started" -ne 1 ]; then
        echo -e "${server_java_label}\t${server_opens_label}\t${client_java_label}\t${client_opens_label}\tSERVER_FAILED_TO_START\t$(tail -3 "$server_log" | tr '\n' ' ')" >> "$SUMMARY"
        kill_server "$server_pid"
        return
    fi

    # --- run headless client connect check ---
    local client_args=("-Djava.awt.headless=true")
    [ "$client_opens" = "1" ] && client_args=("$ADD_OPENS" "${client_args[@]}")

    "$client_java_home/bin/java" "${client_args[@]}" \
        -cp "$CLIENT_TEST_CLASSPATH" \
        mage.client.game.HeadlessConnectCheck "$CONNECT_HOST" "$CONNECT_PORT" "$CONNECT_USER" "$CONNECT_TIMEOUT_SECS" \
        > "$client_log" 2>&1
    local exit_code=$?

    local result detail
    case "$exit_code" in
        0) result="OK";      detail="connected" ;;
        1) result="FAIL";    detail="$(grep -m1 '^RESULT: FAIL' "$client_log" | cut -c1-200)" ;;
        3) result="TIMEOUT"; detail="client hung past ${CONNECT_TIMEOUT_SECS}s" ;;
        *) result="UNKNOWN"; detail="exit=$exit_code: $(tail -3 "$client_log" | tr '\n' ' ')" ;;
    esac

    echo -e "${server_java_label}\t${server_opens_label}\t${client_java_label}\t${client_opens_label}\t${result}\t${detail}" >> "$SUMMARY"

    kill_server "$server_pid"
}

for server_java_label in java8 java26; do
    server_java_home=$([ "$server_java_label" = "java8" ] && echo "$JAVA8_HOME" || echo "$JAVA26_HOME")
    for server_opens in 0 1; do
        for client_java_label in java8 java26; do
            client_java_home=$([ "$client_java_label" = "java8" ] && echo "$JAVA8_HOME" || echo "$JAVA26_HOME")
            for client_opens in 0 1; do
                run_one "$server_java_label" "$server_java_home" "$server_opens" \
                        "$client_java_label" "$client_java_home" "$client_opens"
            done
        done
    done
done

echo
print_emoji_matrix

echo "=== FULL DETAILS ==="
column -t -s $'\t' "$SUMMARY"
echo
echo "Full logs per combination: $RESULTS_DIR/<tag>.server.log / .client.log"