#!/usr/bin/env bash
# Downloads Eclipse Temurin JDK 8 and JDK 26 into ./tmp/java-builds/java8 and
# ./tmp/java-builds/java26 using the Adoptium API, plus optionally Liberica
# JDK 11 Full (bundled JavaFX) into ./tmp/java-builds/java11fx using the
# BellSoft API - needed specifically to COMPILE Mage.Client under a
# JavaFX-aware JDK, since Mage.Client/pom.xml declares
# javafx-controls/swing/web 11.0.2 as a plain Maven dependency (bytecode
# targeting Java 11+), and that dependency fails "cannot access
# javafx.application.Platform ... class file has wrong version 54.0, should
# be 52.0" under --release 8 with a plain OpenJDK 8 (Temurin has no bundled
# JavaFX at all - only Oracle JDK <=10 and vendor "Full"/"FX" builds like
# Liberica bundle JavaFX inside the JDK itself, matching that JDK's own
# bytecode version and avoiding the external dependency entirely).
#
# Liberica JDK 8 Full does NOT appear to exist for linux/aarch64 (only
# linux-amd64/windows/macos builds were found for that old version) - so
# java11fx is used as the closest available FX-bundled JDK to approximate
# a "Java 8 compiler with JavaFX" environment on this platform. This is an
# approximation, not a confirmed-identical substitute for a real Java 8 Full
# build - verify build results accordingly.
#
# Usage:
#   ./test-java-versions-prepare.sh            # Temurin JDK 8 + JDK 26 only
#   ./test-java-versions-prepare.sh java11fx   # also fetch Liberica JDK 11 Full
#
# If you already have a working JDK/JRE somewhere on disk, skip the download
# entirely by pointing at it directly instead of running this script:
#   mkdir -p tmp/java-builds && cp -a /path/to/existing/jdk8/Home tmp/java-builds/java8

set -uo pipefail   # NOTE: no -e anymore - we want to handle curl failures ourselves, not exit silently mid-cleanup

TMP_DIR="./tmp/java-builds"
mkdir -p "$TMP_DIR"

# --- network timeouts so a slow/blocked connection fails fast instead of hanging forever ---
CONNECT_TIMEOUT_SECS=10   # time to establish the TCP connection
MAX_TIME_SECS=180         # total time allowed for the whole download

# --- detect OS ---
case "$(uname -s)" in
    Linux)  OS="linux" ;;
    Darwin) OS="mac" ;;
    *) echo "Unsupported OS: $(uname -s)"; exit 1 ;;
esac

# --- detect arch (Adoptium's naming) ---
case "$(uname -m)" in
    x86_64|amd64)   ARCH="x64" ;;
    aarch64|arm64)  ARCH="aarch64" ;;
    *) echo "Unsupported arch: $(uname -m)"; exit 1 ;;
esac

# --- detect arch (BellSoft/Liberica's filename naming - differs from Adoptium's) ---
case "$(uname -m)" in
    x86_64|amd64)   ARCH_LIBERICA="amd64" ;;
    aarch64|arm64)  ARCH_LIBERICA="aarch64" ;;
    *) echo "Unsupported arch: $(uname -m)"; exit 1 ;;
esac

echo "Detected platform: os=$OS arch=$ARCH (Adoptium) / $ARCH_LIBERICA (BellSoft filenames)"

download_jdk() {
    local feature_version="$1"   # 8 or 26
    local target_dir="$2"        # tmp/java-builds/java8 or tmp/java-builds/java26

    if [ -x "$target_dir/bin/java" ]; then
        echo "== JDK $feature_version already present at $target_dir, checking version =="
        "$target_dir/bin/java" -version
        return 0
    fi

    echo "== Downloading Temurin JDK $feature_version ($OS/$ARCH), timeout ${MAX_TIME_SECS}s =="
    local api_url="https://api.adoptium.net/v3/binary/latest/${feature_version}/ga/${OS}/${ARCH}/jdk/hotspot/normal/eclipse?project=jdk"
    local archive_path="${TMP_DIR}/temurin-${feature_version}.tar.gz"

    # always start from a clean slate - a half-downloaded file from a previous
    # interrupted/killed run must never be mistaken for a complete archive
    rm -f "$archive_path"

    echo "!! download url: $api_url"
    if ! curl -fL --retry 2 \
            --connect-timeout "$CONNECT_TIMEOUT_SECS" \
            --max-time "$MAX_TIME_SECS" \
            -o "$archive_path" "$api_url"; then
        echo "!! FAILED to download JDK $feature_version (network too slow/blocked from here)."
        echo "!! Leftover partial file (if any) removed. Consider copying an existing JDK/JRE instead:"
        echo "!!   mkdir -p '$target_dir' && cp -a /path/to/existing/jdk/Home/. '$target_dir/'"
        rm -f "$archive_path"
        return 1
    fi

    # sanity check: a real JDK tarball is well over 50MB; anything smaller is
    # an error page or a truncated transfer, not a usable archive
    local size_bytes
    size_bytes=$(stat -c%s "$archive_path" 2>/dev/null || stat -f%z "$archive_path" 2>/dev/null || echo 0)
    if [ "$size_bytes" -lt 50000000 ]; then
        echo "!! Downloaded file is only ${size_bytes} bytes - too small to be a real JDK, discarding."
        rm -f "$archive_path"
        return 1
    fi

    mkdir -p "$target_dir"
    # Temurin tarballs contain a single top-level dir (e.g. jdk-26.0.4+7) -
    # strip it so $target_dir/bin/java is the direct path.
    tar -xzf "$archive_path" --strip-components=1 -C "$target_dir"
    rm -f "$archive_path"

    echo "== JDK $feature_version installed at $target_dir =="
    "$target_dir/bin/java" -version
}

download_liberica_full() {
    local feature_version="$1"   # e.g. 11
    local target_dir="$2"        # e.g. tmp/java-builds/java11fx

    if [ -x "$target_dir/bin/javac" ]; then
        echo "== Liberica JDK $feature_version Full already present at $target_dir, checking version =="
        "$target_dir/bin/java" -version
        return 0
    fi

    echo "== Downloading Liberica JDK $feature_version Full ($OS/$ARCH_LIBERICA, bundled JavaFX), timeout ${MAX_TIME_SECS}s =="

    # BellSoft's "arch" filter value doesn't necessarily match uname -m or even
    # their own filename convention (their docs show "arch=x86" for amd64
    # filenames) - ask their own discovery endpoint instead of guessing a
    # single hardcoded value, and try a few candidates against the actual
    # releases endpoint since the discovery list alone doesn't tell us which
    # exact string that endpoint expects.
    local valid_archs
    valid_archs=$(curl -fsL --connect-timeout "$CONNECT_TIMEOUT_SECS" --max-time 20 "https://api.bell-sw.com/v1/liberica/architectures")
    echo "!! BellSoft reports valid arch values: $valid_archs"

    # BellSoft's "architecture" field/filter uses family names (arm/x86/ppc/...),
    # not amd64/aarch64 - confirmed empirically: arch=arm + bundle-type=jdk-full
    # is the correct combination for linux/aarch64.
    local arch_family
    case "$ARCH_LIBERICA" in
        amd64) arch_family="x86" ;;
        aarch64) arch_family="arm" ;;
        *) arch_family="$ARCH_LIBERICA" ;;
    esac
    local arch_candidates=("$arch_family")
    local download_url=""
    local api_url=""
    for candidate in "${arch_candidates[@]}"; do
        api_url="https://api.bell-sw.com/v1/liberica/releases?version-feature=${feature_version}&version-modifier=latest&bitness=64&os=${OS}&arch=${candidate}&package-type=tar.gz&bundle-type=jdk-full&output=text&fields=downloadUrl"
        echo "!! trying arch=$candidate : $api_url"
        download_url=$(curl -fsL --connect-timeout "$CONNECT_TIMEOUT_SECS" --max-time 30 "$api_url" | tr -d '\r' | grep -m1 '^https://')
        # a real success is a plain https URL; an error response is JSON (starts with '{'); no match is empty
        if [[ "$download_url" == https://* ]]; then
            echo "!! arch=$candidate worked"
            break
        fi
        echo "!! arch=$candidate did not return a usable URL (response: $download_url)"
        download_url=""
    done

    local archive_path="${TMP_DIR}/liberica-jdk${feature_version}-full.tar.gz"
    rm -f "$archive_path"

    if [ -z "$download_url" ]; then
        echo "!! FAILED to resolve a Liberica JDK $feature_version Full download URL from the BellSoft API."
        echo "!! Either the API is unreachable from here, or no matching release exists for this OS/arch/version combination."
        echo "!! Manual fallback: pick a build yourself at https://bell-sw.com/pages/downloads/"
        echo "!!   (choose Java $feature_version, your OS/arch, and the \"Full\" package - bundles JavaFX),"
        echo "!!   then: mkdir -p '$target_dir' && tar -xzf <downloaded.tar.gz> --strip-components=1 -C '$target_dir'"
        return 1
    fi

    echo "!! download url: $download_url"
    if ! curl -fL --retry 2 \
            --connect-timeout "$CONNECT_TIMEOUT_SECS" \
            --max-time "$MAX_TIME_SECS" \
            -o "$archive_path" "$download_url"; then
        echo "!! FAILED to download Liberica JDK $feature_version Full (network too slow/blocked from here)."
        rm -f "$archive_path"
        return 1
    fi

    local size_bytes
    size_bytes=$(stat -c%s "$archive_path" 2>/dev/null || stat -f%z "$archive_path" 2>/dev/null || echo 0)
    if [ "$size_bytes" -lt 50000000 ]; then
        echo "!! Downloaded file is only ${size_bytes} bytes - too small to be a real JDK, discarding."
        rm -f "$archive_path"
        return 1
    fi

    mkdir -p "$target_dir"
    tar -xzf "$archive_path" --strip-components=1 -C "$target_dir"
    rm -f "$archive_path"

    echo "== Liberica JDK $feature_version Full installed at $target_dir =="
    "$target_dir/bin/java" -version
    if [ ! -x "$target_dir/bin/javac" ]; then
        echo "!! WARNING: no bin/javac found - this may be a JRE-only package, not a JDK. Check the archive contents."
        return 1
    fi
}

overall_ok=0
download_jdk 8  "${TMP_DIR}/java8"  || overall_ok=1
download_jdk 26 "${TMP_DIR}/java26" || overall_ok=1

if [ "${1:-}" = "java11fx" ]; then
    download_liberica_full 11 "${TMP_DIR}/java11fx" || overall_ok=1
fi

echo
if [ "$overall_ok" -eq 0 ]; then
    echo "Done. Use these as JAVA_HOME values for the matrix test script:"
    echo "  Java 8 home:  $(realpath "${TMP_DIR}/java8")"
    echo "  Java 26 home: $(realpath "${TMP_DIR}/java26")"
    [ -d "${TMP_DIR}/java11fx" ] && echo "  Java 11 Full (with JavaFX) home: $(realpath "${TMP_DIR}/java11fx")"
else
    echo "One or more downloads failed - see messages above."
    echo "If this environment's network can't reach api.adoptium.net/api.bell-sw.com reliably,"
    echo "copy an already-installed JDK/JRE into the relevant tmp/java-builds/<label> manually instead."
fi
exit "$overall_ok"