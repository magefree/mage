#!/usr/bin/env python3
"""Update offline image-download groups from Scryfall's all_cards JSONL stream.

Usage: python Utils/update_image_download_sets.py /path/to/all_cards.jsonl[.gz]
       --metadata /path/to/all-cards-metadata.json [--check]
Use jsonl_download_uri from https://api.scryfall.com/bulk-data/all-cards,
also used by XMage's Scryfall downloader. No network access is performed here.
The groups select whole sets, including partial Arena releases and mixed sets;
they do not enforce individual-card bans or physical-printing tournament rules.
"""

import argparse
import gzip
import hashlib
import json
import pathlib
import re


MIN_PRINTINGS = 100000
MIN_SETS = 500


def read_printings(cards_path):
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
            games = card['games']
            vintage = card['legalities']['vintage']
            if vintage not in ('legal', 'restricted', 'banned', 'not_legal'):
                raise ValueError('Unknown Vintage legality: ' + str(vintage))
            # Arena printing availability, not arena_code, individual bans, or set type.
            row[0] += 'arena' in games
            # Include banned/restricted cards: this is an image group, not a deck validator.
            row[1] += (any(game in games for game in ('paper', 'mtgo'))
                       and vintage in ('legal', 'restricted', 'banned'))
    return counts, digest.hexdigest(), records


def update(cards_path, root, metadata_path, check=False):
    metadata = json.loads(metadata_path.read_text(encoding='utf-8-sig'))
    if (metadata.get('type') != 'all_cards' or not metadata.get('jsonl_download_uri')
            or not metadata.get('updated_at')):
        raise ValueError('Expected Scryfall all_cards metadata; no files written')
    if cards_path.suffix == '.gz' and cards_path.stat().st_size != metadata['compressed_size']:
        raise ValueError('Compressed input size differs from the snapshot metadata; no files written')
    counts, digest, records = read_printings(cards_path)

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

    if records < MIN_PRINTINGS or len(counts) < MIN_SETS or len(inventory) < MIN_SETS:
        raise ValueError('Incomplete input or expansion inventory; no files written')
    provenance = ('# Scryfall all_cards JSONL\n'
                  '# https://api.scryfall.com/bulk-data/all-cards\n'
                  '# Uncompressed SHA256: ' + digest + '\n'
                  '# Records: ' + str(records) + '; implemented expansions: ' + str(len(inventory)) + '\n'
                  '# Snapshot: ' + metadata['jsonl_download_uri'] + '\n'
                  '# Snapshot updated: ' + metadata['updated_at'] + '\n')
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
    outputs = ((resources / 'download-format-sets.properties', production),
               (tests / 'download-format-coverage.csv', fixture))
    if check:
        for path, expected in outputs:
            if not path.is_file() or path.read_text(encoding='utf-8') != expected:
                raise ValueError('Generated inventory differs: ' + str(path))
    else:
        for path, content in outputs:
            path.parent.mkdir(parents=True, exist_ok=True)
            with path.open('w', encoding='utf-8', newline='\n') as output:
                output.write(content)
    print('Audited', len(inventory), 'sets from', records, 'printings')
    print('Historic:', sum(bool(row[1][0]) for row in inventory.values()))
    print('Vintage:', sum(bool(row[1][1]) for row in inventory.values()))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('cards', type=pathlib.Path)
    parser.add_argument('--metadata', type=pathlib.Path, required=True)
    parser.add_argument('--check', action='store_true', help='verify the checked-in files without changing them')
    args = parser.parse_args()
    update(args.cards, pathlib.Path(__file__).resolve().parent.parent, args.metadata, args.check)
