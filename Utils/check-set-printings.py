#!/usr/bin/env python3
"""
Report cards that are implemented but whose printing (collector number) is not
registered in the Java set class for the given set code.

Usage: python3 Utils/check-set-printings.py <SET_CODE>

Scryfall data is cached in Utils/data/<CODE>-scryfall.json (refetched if older than 24h).
"""
import json
import os
import re
import sys
import time
import unicodedata
from urllib.error import HTTPError, URLError
from urllib.parse import urlencode
from urllib.request import Request, urlopen

UTILS_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(UTILS_DIR, "data")
SETS_DATA_FILE = os.path.join(UTILS_DIR, "mtg-sets-data.txt")
SETS_JAVA_DIR = os.path.join(UTILS_DIR, "..", "Mage.Sets", "src", "mage", "sets")

SET_CARD_INFO_RE = re.compile(r'new SetCardInfo\(\s*"((?:[^"\\]|\\.)*)"\s*,\s*("?[^",\s]+"?)\s*,')


def is_file_stale(path, hours=24):
    return not os.path.exists(path) or time.time() - os.path.getmtime(path) > hours * 3600


def fetch_data(url):
    cards = []
    while url:
        req = Request(url, headers={"User-Agent": "mage-scryfall-sync/1.0", "Accept": "application/json"})
        try:
            with urlopen(req) as response:
                payload = json.loads(response.read().decode("utf-8"))
        except HTTPError as e:
            raise SystemExit(f"HTTP error {e.code} for {url}")
        except URLError as e:
            raise SystemExit(f"Network error for {url}: {e.reason}")
        cards.extend(payload["data"])
        url = payload.get("next_page")
    return {"data": cards}


def load_scryfall(code):
    path = os.path.join(DATA_DIR, code.upper() + "-scryfall.json")
    if is_file_stale(path):
        print(f"Fetching fresh data for {code} ...")
        query = urlencode({"q": f"s:{code}", "order": "set", "unique": "prints", "include_extras": "true"})
        os.makedirs(DATA_DIR, exist_ok=True)
        with open(path, "w", encoding="utf-8") as f:
            json.dump(fetch_data(f"https://api.scryfall.com/cards/search?{query}"), f)
    else:
        print(f"Using cached data for {code}")
    with open(path, encoding="utf-8") as f:
        return json.load(f)["data"]


def normalize(name):
    name = unicodedata.normalize("NFD", name.replace('\\"', '"'))
    return "".join(c for c in name if unicodedata.category(c) != "Mn").lower()


def set_name_for_code(code):
    with open(SETS_DATA_FILE, encoding="utf-8") as f:
        for line in f:
            parts = line.rstrip("\n").split("|")
            if len(parts) >= 2 and parts[1].upper() == code.upper():
                return parts[0]
    raise SystemExit(f"Set code {code} not found in {SETS_DATA_FILE}")


def read_registrations(java_file):
    """Returns {normalized name: set of collector numbers} for a Java file."""
    result = {}
    with open(java_file, encoding="utf-8") as f:
        for name, number in SET_CARD_INFO_RE.findall(f.read()):
            result.setdefault(normalize(name), set()).add(number.strip('"').lower())
    return result


def find_set_class(code, set_name):
    pattern = re.compile(r'super\(\s*"' + re.escape(set_name) + r'"\s*,\s*"' + re.escape(code.upper()) + '"')
    for file in sorted(os.listdir(SETS_JAVA_DIR)):
        if not file.endswith(".java"):
            continue
        path = os.path.join(SETS_JAVA_DIR, file)
        with open(path, encoding="utf-8") as f:
            if pattern.search(f.read()):
                return path
    raise SystemExit(f"No Java set class found for '{set_name}' ({code.upper()}) in {SETS_JAVA_DIR}")


def all_implemented_names():
    names = set()
    for file in os.listdir(SETS_JAVA_DIR):
        if file.endswith(".java"):
            names.update(read_registrations(os.path.join(SETS_JAVA_DIR, file)))
    return names


def card_name_variants(card):
    full = card["name"]
    variants = {normalize(full)}
    variants.update(normalize(part.strip()) for part in full.split("//"))
    return variants


def main():
    if len(sys.argv) != 2 or not re.fullmatch(r"[A-Za-z0-9]+", sys.argv[1]):
        raise SystemExit(f"Usage: {os.path.basename(sys.argv[0])} <SET_CODE>")
    code = sys.argv[1]

    set_name = set_name_for_code(code)
    java_file = find_set_class(code, set_name)
    print(f"{set_name} ({code.upper()}) -> {os.path.relpath(java_file, os.path.join(UTILS_DIR, '..'))}")

    registered = read_registrations(java_file)
    implemented = all_implemented_names()

    missing = []
    for card in load_scryfall(code):
        number = card["collector_number"].lower()
        variants = card_name_variants(card)
        if not variants & implemented:
            continue  # card itself not implemented, out of scope
        if any(number in registered.get(v, ()) for v in variants):
            continue
        missing.append((card["collector_number"], card["name"]))

    if not missing:
        print("All printings of implemented cards are registered.")
        return

    def sort_key(item):
        digits = re.sub(r"\D", "", item[0])
        return int(digits) if digits else 0, item[0]

    print(f"\n{len(missing)} implemented printing(s) not registered:")
    for number, name in sorted(missing, key=sort_key):
        print(f"  {number}\t{name}")


if __name__ == "__main__":
    main()
