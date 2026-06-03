import json
import os
import re
import sys
import time
import unicodedata
from urllib.error import HTTPError, URLError
from urllib.parse import urlencode
from urllib.request import Request, urlopen

# Function to fetch data from a given URL
def fetch_data(url):
    all_data = []
    while url:
        req = Request(
            url,
            headers={
                'User-Agent': 'mage-scryfall-sync/1.0',
                'Accept': 'application/json'
            }
        )
        try:
            with urlopen(req) as response:
                payload = json.loads(response.read().decode('utf-8'))
        except HTTPError as e:
            raise RuntimeError(f"HTTP error {e.code} for {url}") from e
        except URLError as e:
            raise RuntimeError(f"Network error for {url}: {e.reason}") from e

        all_data.extend(payload['data'])
        url = payload.get('next_page')
    return {'data': all_data}

# Function to check if file is stale (older than 12 hours)
def is_file_stale(file_path, hours=12):
    if not os.path.exists(file_path):
        return True  # File doesn't exist, so it's "stale"

    file_mod_time = os.path.getmtime(file_path)
    file_age = time.time() - file_mod_time
    return file_age > (hours * 3600)  # Convert hours to seconds

# Convert collector number to int
def extract_collector_number(card_json):
    try:
        string = re.sub(r'\D', '', card_json['collector_number'])
        if not string:
            return 0
        return int(string)
    except KeyError:
        return 0

# Function to replace accented characters with ASCII equivalents
def replace_accented_chars(text):
    """
    Replace accented characters with their ASCII equivalents
    e.g., ā -> a, é -> e, ñ -> n, etc.
    """
    # Normalize to NFD (decomposed form) and remove combining characters
    normalized = unicodedata.normalize('NFD', text)
    ascii_text = ''.join(char for char in normalized if unicodedata.category(char) != 'Mn')
    return ascii_text

# Function to create a card output line
def create_face_line(set_name, collector_number, rarity, card, power, toughness):
    name = replace_accented_chars(card.get('name', '').replace('"', ''))
    mana_cost = card.get('mana_cost', '')
    type_line = card.get('type_line', '').replace('—', '-')
    if not power:
        power = card.get('power', '')
    if not toughness:
        toughness = card.get('toughness', '')
    oracle_text = (
        card.get('oracle_text', '')
        .replace('\n', '$')
        .replace('—', '--')
        .replace('•', '*')
        .strip()
    )
    oracle_text = re.sub(r" \(.*?\)", "", oracle_text)
    # Replace accented characters in oracle text
    oracle_text = replace_accented_chars(oracle_text)

    if card.get('loyalty'):
        toughness = card.get('loyalty', '')
        return f"{name}|{set_name}|{collector_number}|{rarity}|{mana_cost}|{type_line}|{toughness}|{oracle_text}|\n"
    else:
        return f"{name}|{set_name}|{collector_number}|{rarity}|{mana_cost}|{type_line}|{power}|{toughness}|{oracle_text}|\n"

def create_card_line(set_name, collector_number, rarity, card):
    return create_face_line(set_name, collector_number, rarity, card, card.get('power', ''), card.get('toughness', ''))

rarity_map = {
    'common': 'C',
    'uncommon': 'U',
    'rare': 'R',
    'mythic': 'M'
}

def parse_set_codes(argv):
    if len(argv) > 1:
        candidates = argv[1:]
    else:
        user_input = input("Enter space-separated set codes: ").strip()
        if not user_input:
            raise SystemExit("No set codes provided.")
        candidates = user_input.split()

    set_codes = []
    for code in candidates:
        normalized = code.strip().lower()
        if not re.fullmatch(r'[a-z0-9]+', normalized):
            raise SystemExit(f"Invalid set code: {code}")
        set_codes.append(normalized)

    return set_codes

# Loop through each set
sets_data = parse_set_codes(sys.argv)
for set_code in sets_data:
    file_path = set_code.upper() + "-scryfall.json"

    # Check if file doesn't exist or is stale (older than 24 hours)
    if is_file_stale(file_path, 24):
        print(f"Fetching fresh data for {set_code} (file is stale or doesn't exist)...")
        query = urlencode({'q': f'set:{set_code} order:set unique:prints'})
        jsn = fetch_data(f"https://api.scryfall.com/cards/search?{query}")
        with open(file_path, 'w') as file:
            json.dump(jsn, file)
        print(f"Saved fresh data to {file_path}")
    else:
        print(f"Using existing data for {set_code} (file is fresh)")

    output = ""
    with open(file_path, 'r', encoding='utf-8') as file:
        cards_data = json.load(file)['data']

    # Loop through each set, handling pagination
    # cards_data.sort(key=extract_collector_number)
    # cards_data_sorted = sorted(cards_data['data'], key=lambda x: int(x['collector_number']))
    for card in cards_data:
        rarity = rarity_map.get(card['rarity'].lower(), 'C')
        p = card.get('power', '')
        t = card.get('toughness', '')
        if 'card_faces' in card:
            for face in card['card_faces']:
                output += create_face_line(card['set_name'], card['collector_number'], rarity, face, p, t)
        else:
            output += create_card_line(card['set_name'], card['collector_number'], rarity, card)

    # Save the output to a file
    output_file_path = f"{set_code}-data.txt"
    with open(output_file_path, 'w', encoding='utf-8') as file:
        file.write(output)
    print(f"Saved output to {os.path.abspath(output_file_path)}")
