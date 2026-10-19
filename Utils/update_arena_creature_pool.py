#!/usr/bin/env python3
"""Generate the offline Arena creature catalog (no network access).

python Utils/update_arena_creature_pool.py /path/to/Raw_CardDatabase*.mtga \
    /path/to/all_cards.jsonl[.gz] --metadata /path/to/all-cards-metadata.json

The read-only Arena client database includes event/conjure-only cards. Scryfall's
printing-level games field supplements newer releases; format legality and
collection ownership are deliberately irrelevant. See arena-creature-pool.md.
"""

import argparse
from contextlib import closing
import gzip
import hashlib
import html
import json
import pathlib
import re
import sqlite3

from arena_creature_pool_io import sha256_file, write_text_atomically


RESOURCE = pathlib.Path('Mage/src/main/resources/arena-creature-pool.tsv')


def add(cards, name, mana_value):
    if not name or '\t' in name or '\n' in name or mana_value < 0:
        raise ValueError('Invalid creature identity: ' + repr(name))
    if name in cards and cards[name] != mana_value:
        raise ValueError('Conflicting mana values for ' + name)
    cards[name] = mana_value


def read_arena(path):
    cards = {}
    with closing(sqlite3.connect(path.resolve().as_uri() + '?mode=ro&immutable=1', uri=True)) as db:
        versions = dict(db.execute('SELECT Type, Version FROM Versions'))
        rows = db.execute('''SELECT l.Loc, c.Order_CMCWithXLast FROM Cards c
            JOIN Localizations_enUS l ON c.TitleId = l.LocId AND l.Formatted = 1
            WHERE c.IsToken = 0 AND c.IsPrimaryCard = 1 AND c.IsRebalanced = 0
            AND instr(',' || c.Types || ',', ',2,') > 0''')
        for name, cmc in rows:
            name = html.unescape(re.sub(r'<[^>]*>', '', name)).replace("\\'", "'")
            # The client sorts costs containing X after ordinary costs by adding
            # 1000. Outside the stack X contributes zero to mana value.
            add(cards, name, cmc % 1000)
    return cards, versions, sha256_file(path)


def read_scryfall(path):
    cards = {}
    digest = hashlib.sha256()
    records = 0
    opener = gzip.open if path.suffix == '.gz' else open
    with opener(path, 'rb') as stream:
        for line in stream:
            digest.update(line)
            if not line.strip():
                continue
            card = json.loads(line)
            records += 1
            if ('arena' not in card['games'] or card.get('layout') in
                    ('token', 'double_faced_token', 'emblem', 'art_series')):
                continue
            # Related-part IDs may point to the regular printing even on an
            # alternate-art result. Compare the identity as well as the ID.
            if any(part['component'] == 'meld_result' and
                   (part['id'] == card['id'] or part.get('name') == card['name'])
                   for part in card.get('all_parts', [])):
                continue
            front = card.get('card_faces', [card])[0]
            # A- versions are alternate rules for an existing identity, not an
            # additional random outcome. XMage currently implements originals.
            if 'Creature' not in front['type_line'].split(' — ')[0].split() or front['name'].startswith('A-'):
                continue
            cmc = card['cmc']
            if cmc != int(cmc):
                raise ValueError('Non-integral Arena creature mana value')
            add(cards, front['name'], int(cmc))
    return cards, digest.hexdigest(), records


def merge_catalogs(native, supplement):
    cards = {}
    identities = {}
    # Prefer Scryfall's current canonical spelling if the client capitalizes a
    # name differently. This must not add a second outcome (Sol'Kanar/Sol'kanar).
    for source in (native, supplement):
        for name, cmc in source.items():
            key = name.casefold()
            previous = identities.get(key)
            if previous is not None:
                if cards[previous] != cmc:
                    raise ValueError('Conflicting mana values for ' + name)
                del cards[previous]
            add(cards, name, cmc)
            identities[key] = name
    return cards


def update(arena_path, cards_path, metadata_path, root, check=False):
    metadata = json.loads(metadata_path.read_text(encoding='utf-8-sig'))
    if metadata.get('type') != 'all_cards' or not metadata.get('updated_at') or not metadata.get('jsonl_download_uri'):
        raise ValueError('Expected Scryfall all_cards snapshot metadata')
    if cards_path.suffix == '.gz' and cards_path.stat().st_size != metadata['compressed_size']:
        raise ValueError('Compressed size differs from snapshot metadata')
    cards, versions, arena_digest = read_arena(arena_path)
    supplement, scryfall_digest, records = read_scryfall(cards_path)
    if len(cards) < 5000 or len(supplement) < 5000 or records < 100000:
        raise ValueError('Incomplete source snapshot; no files written')
    native_count = len(merge_catalogs(cards, {}))
    cards = merge_catalogs(cards, supplement)
    content = ('# Arena primary, non-token, original-rule creature identities; mana value TAB front-face name\n'
               '# Arena client Data: ' + versions['Data'] + '; GRP: ' + versions['GRP'] + '\n'
               '# Arena database SHA256: ' + arena_digest + '\n'
               '# Scryfall snapshot: ' + metadata['jsonl_download_uri'] + '\n'
               '# Scryfall updated: ' + metadata['updated_at'] + '\n'
               '# Scryfall uncompressed SHA256: ' + scryfall_digest + '\n'
               '# Arena identities: ' + str(native_count) + '; supplemented identities: '
               + str(len(cards) - native_count) + '; total: ' + str(len(cards)) + '\n'
               '# Refresh: Utils/update_arena_creature_pool.py; policy: Utils/arena-creature-pool.md\n')
    content += ''.join(str(cmc) + '\t' + name + '\n'
                       for name, cmc in sorted(cards.items(), key=lambda row: (row[1], row[0])))
    target = root / RESOURCE
    if check:
        if target.read_text(encoding='utf-8') != content:
            raise ValueError('Generated Arena catalog differs: ' + str(target))
    else:
        write_text_atomically(target, content)
    print('Arena creature identities:', len(cards), '(client:', native_count,
          'supplement:', len(cards) - native_count, ')')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('arena_database', type=pathlib.Path)
    parser.add_argument('cards', type=pathlib.Path)
    parser.add_argument('--metadata', type=pathlib.Path, required=True)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    update(args.arena_database, args.cards, args.metadata,
           pathlib.Path(__file__).resolve().parent.parent, args.check)
