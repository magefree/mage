#!/usr/bin/python3
# sync_sld_printings.py
#
# This script is used to synchronize SLD cards as they're intentionally not
# included in mtg-cards-data.txt
#
# It will both report cards that are missing implementations and register and
# update printings for cards that are.

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
def extract_collector_number(card):
    try:
        string = re.sub(r'\D', '', card['collector_number'])
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

def normalise_rarity(card):
    if card.get('type_line', '').startswith('Basic'):
        return 'LAND'
    else:
        return card.get('rarity', '').upper()

def normalise_collector_number(collector_number):
    """
    Normalize collector number by replacing symbols and mapping overrides.
    """
    # Replace stars, and phyrexian symbols
    normalized_number = collector_number.replace('★', '*').replace('Φ', 'Ph')
    return normalized_number

def generate_class_name(card_name):
    """
    Generate a class name from the card name by:
    - Replacing accented characters with ASCII equivalents
    - Converting to CamelCase based on space boundaries
    - Removing non-alphanumeric characters and spaces
    - Prefixing with parent class if basic land or not
    """
    ascii_name = replace_accented_chars(card_name)
    camel_case_name = ''.join(word.capitalize() for word in ascii_name.replace('-', ' ').split())
    cleaned_name = re.sub(r'[^a-zA-Z0-9 ]', '', camel_case_name)
    class_name = cleaned_name.strip().replace(' ', '')
    # Prefix full class name
    if class_name.lower() in ['plains', 'island', 'swamp', 'mountain', 'forest']:
        class_name = f"mage.cards.basiclands.{class_name}.class"
    else:
        class_name = f"mage.cards.{class_name[0].lower()}.{class_name}.class"
    return class_name

def derive_graphic_info(card):
    """
    Derive graphic info based on full art, frame, and multiple printings present.
    """
    if not card['multiple_prints']:
        if card['full_art']:
            return 'FULL_ART'
        elif card['frame'] != '2015':
            return 'RETRO_ART'
        else:
            return 'NORMAL_ART'
    else:
        if card['full_art']:
            if card['rarity'] == 'LAND':
                return 'FULL_ART_BFZ_VARIOUS'
            else:
                return 'FULL_ART_USE_VARIOUS'
        elif card['frame'] != '2015':
            return 'RETRO_ART_USE_VARIOUS'
        else:
            return 'NON_FULL_USE_VARIOUS'

def gen_entry(card):
    """
    Generate the Java code entry for a card.
    """
    entry = '        '

    if not card['implemented']:
        entry += '// '

    entry += 'cards.add(new SetCardInfo("'
    entry += card["name"].replace('"', '\\"') + '", '

    # Wrap non integer collector numbers in quotes
    if re.match(r'^\d+$', card['collector_number']):
        entry += f'{card["collector_number"]}'
    else:
        entry += f'"{card["collector_number"]}"'

    entry += f', Rarity.{card["rarity"]}, {card["class_name"]}'

    if card['graphic_info'] != 'NORMAL_ART':
        entry += f', {card["graphic_info"]}'

    entry += '));\n'
    return entry

# Check if file doesn't exist or is stale (older than 24 hours)
file_path = "SLD-scryfall.json"
if is_file_stale(file_path, 24):
    print(f"Fetching fresh data for SLD (file is stale or doesn't exist)...")
    query = urlencode({'q': 's:sld -t:token -t:Card', 'order': 'set', 'unique': 'prints', 'include_extras': 'false'})
    jsn = fetch_data(f"https://api.scryfall.com/cards/search?{query}")
    print(f"https://api.scryfall.com/cards/search?{query}")
    with open(file_path, 'w') as file:
        json.dump(jsn, file)
    print(f"Saved fresh data to {file_path}")
else:
    print(f"Using existing data for SLD (file is fresh)")

with open(file_path, 'r', encoding='utf-8') as file:
    cards_data = json.load(file)['data']

cards = []
for card_data in cards_data:
    card = {}
    card['collector_number'] = normalise_collector_number(card_data['collector_number'])
    card['rarity'] = normalise_rarity(card_data)
    card['full_art'] = card_data.get('full_art', False)
    card['frame'] = card_data.get('frame', '2015')  # Default to '2015'
    card['multiple_prints'] = False  # Placeholder
    card['implemented'] = False  # Placeholder
    card['graphic_info'] = 'NORMAL_ART' # Placeholder

    if card_data['layout'] == 'reversible_card' or card_data['layout'] == 'transform' or card_data['layout'] == 'modal_dfc':
        # Front face
        card['name'] = replace_accented_chars(card_data['card_faces'][0]['name'])
        card['class_name'] = generate_class_name(card_data['card_faces'][0]['name'])
        # Back face
        card['name'] = replace_accented_chars(card_data['card_faces'][1]['name'])
        card['class_name'] = generate_class_name(card_data['card_faces'][1]['name'])
    else:
        # e.g. for Adventures and normal cards, use the printed main card information
        card['name'] = replace_accented_chars(card_data['name'])
        card['class_name'] = generate_class_name(card_data['name'])

    cards.append(card)

unique_names = list(dict.fromkeys(card['name'] for card in cards))
for name in unique_names:
    matching_cards = [card for card in cards if card['name'] == name]
    if len(matching_cards) > 1:
        multiple_prints = True

    class_name = matching_cards[0]['class_name']
    class_file_path = os.path.join('..', 'Mage.Sets', 'src', *class_name.split('.')[:-1]) + '.java'

    for card in cards:
        if card['name'] == name:
            card['multiple_prints'] = len(matching_cards) > 1
            card['implemented'] = os.path.exists(class_file_path) or name in ['Plains', 'Island', 'Swamp', 'Mountain', 'Forest']

for card in cards:
    card['graphic_info'] = derive_graphic_info(card) # Do this now that we have knowledge of the set in aggregate

set_file = os.path.join('..', 'Mage.Sets', 'src', 'mage', 'sets', 'SecretLairDrop.java')
lines = []
with open (set_file, "r", encoding="utf-8") as f:
    for line in f:
        if re.search(r'// This is generated code from the script: Utils/sync-sld-printings.py', line):
            break
        lines.append(line)

lines.append('    // This is generated code from the script: Utils/sync-sld-printings.py\n\n')

lines.append('    private void addPart1() {\n')
for card in cards:
    if extract_collector_number(card) < 1001:
        lines.append(gen_entry(card))
lines.append('    }\n\n')

lines.append('    private void addPart2() {\n')
for card in cards:
    if extract_collector_number(card) >= 1001 and extract_collector_number(card) < 2001:
        lines.append(gen_entry(card))
lines.append('    }\n\n')

lines.append('    private void addPart3() {\n')
for card in cards:
    if extract_collector_number(card) >= 2001:
        lines.append(gen_entry(card))
lines.append('    }\n\n')

lines.append('}\n')

with open (set_file, "w", encoding="utf-8") as f:
    f.writelines(lines)

# TODO: I could probably make this just check for an entry in that file for each double faced card and warn if it's missing, but for now, just warn the user to check the diff.
print(f"WARNING: Review the git diff. If any double faced cards were added (indicated by collector numbers ending with alphabetical characters), you must add the second side to directDownloadLinks in ScryfallImageSupportCards.java")
