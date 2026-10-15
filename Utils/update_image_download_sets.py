#!/usr/bin/env python3
"""Update offline image-download groups from Scryfall's all_cards JSONL stream.

Usage: python Utils/update_image_download_sets.py /path/to/all_cards.jsonl[.gz]
       [--metadata /path/to/all-cards-metadata.json]
Use jsonl_download_uri from https://api.scryfall.com/bulk-data/all-cards,
also used by XMage's Scryfall downloader. No network access is performed here.
The groups select whole sets, including partial Arena releases and mixed sets;
they do not enforce individual-card bans or physical-printing tournament rules.
"""

import argparse
import datetime
import gzip
import hashlib
import json
import pathlib
import re


def update(cards_path, root, metadata_path=None):
    counts = {}
    digest = hashlib.sha256()
    records = 0
    opener = gzip.open if cards_path.suffix == '.gz' else open
    with opener(cards_path, 'rb') as cards:
        for line in cards:
            digest.update(line)
            if not line.strip():
                continue
            card = json.loads(line)
            records += 1
            code = card['set'].upper()
            row = counts.setdefault(code, [0, 0])
            games = card.get('games', [])
            # Arena printing availability, not arena_code, individual bans, or set type.
            row[0] += 'arena' in games
            # Include banned/restricted cards: this is an image group, not a deck validator.
            row[1] += (any(game in games for game in ('paper', 'mtgo'))
                       and card.get('legalities', {}).get('vintage')
                       in ('legal', 'restricted', 'banned'))

    inventory = {}
    declaration = re.compile(r'super\("(?:[^"\\]|\\.)*",\s*"([^"]+)".*?SetType\.(\w+)', re.S)
    for source in sorted((root / 'Mage.Sets/src/mage/sets').glob('*.java')):
        match = declaration.search(source.read_text(encoding='utf-8'))
        if not match:
            raise ValueError('Cannot identify expansion metadata in ' + str(source))
        code, set_type = match.groups()
        if code in inventory:
            raise ValueError('Duplicate expansion code: ' + code)
        inventory[code] = (set_type, counts.get(code, [0, 0]))

    if records < 100000 or len(counts) < 500 or len(inventory) < 500:
        raise ValueError('Incomplete input or expansion inventory; no files written')
    date = datetime.date.today().isoformat()
    provenance = ('# Scryfall all_cards JSONL, audited ' + date + '\n'
                  '# https://api.scryfall.com/bulk-data/all-cards\n'
                  '# Uncompressed SHA256: ' + digest.hexdigest() + '\n'
                  '# Records: ' + str(records) + '; implemented expansions: ' + str(len(inventory)) + '\n')
    if metadata_path is not None:
        metadata = json.loads(metadata_path.read_text(encoding='utf-8-sig'))
        if metadata.get('type') != 'all_cards' or not metadata.get('jsonl_download_uri'):
            raise ValueError('Expected Scryfall all_cards metadata; no files written')
        provenance += '# Snapshot: ' + metadata['jsonl_download_uri'] + '\n'
    production = (provenance
                  + '# H: at least one Arena printing. V: a paper/MTGO printing with\n'
                  + '# Vintage legal/restricted/banned status. Download the ENTIRE selected set.\n'
                  + '# Empty entries are audited exclusions; new unaudited sets use type defaults.\n'
                  + '# Refresh with Utils/update_image_download_sets.py.\n')
    fixture = provenance + '# code,xmage_type,arena_printings,tabletop_vintage_printings\n'
    for code, (set_type, (arena, vintage)) in sorted(inventory.items()):
        production += code + '=' + ('H' if arena else '') + ('V' if vintage else '') + '\n'
        fixture += ','.join((code, set_type, str(arena), str(vintage))) + '\n'

    resources = root / 'Mage.Client/src/main/resources/org/mage/plugins/card/images'
    tests = root / 'Mage.Client/src/test/resources/org/mage/plugins/card/images'
    resources.mkdir(parents=True, exist_ok=True)
    tests.mkdir(parents=True, exist_ok=True)
    (resources / 'download-format-sets.properties').write_text(production, encoding='utf-8')
    (tests / 'download-format-coverage.csv').write_text(fixture, encoding='utf-8')
    print('Audited', len(inventory), 'sets from', records, 'printings')
    print('Historic:', sum(bool(row[1][0]) for row in inventory.values()))
    print('Vintage:', sum(bool(row[1][1]) for row in inventory.values()))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('cards', type=pathlib.Path)
    parser.add_argument('--metadata', type=pathlib.Path)
    args = parser.parse_args()
    update(args.cards, pathlib.Path(__file__).resolve().parent.parent, args.metadata)
