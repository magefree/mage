#!/usr/bin/env python3
"""Re-audit the original 571 missing entries against a CardRepository CSV export.

See arena-creature-pool.md for the read-only export query. This tool checks names,
all face/alternate-name fields, mana values, set metadata, source registrations
and class filenames. It never treats a fuzzy match as an implementation.
"""

import argparse
import collections
import csv
import io
import json
import pathlib
import re

from arena_creature_pool_io import write_text_atomically


REPORT = pathlib.Path('Utils/arena-creature-pool-audit.tsv')
EXPECTED_AUDIT_ENTRIES = 571
MIN_REPOSITORY_RECORDS = 50000
FACE_FIELDS = ('SECONDSIDENAME', 'DOUBLEFACEDSECONDSIDENAME', 'SPELLOPTIONCARDNAME',
               'FLIPCARDNAME', 'MELDSTOCARDNAME')


def name_normalizer(root):
    # Use the same explicit transliterations as XMage's deck importer/selector.
    source = (root / 'Mage/src/main/java/mage/cards/decks/CardNameUtil.java').read_text(encoding='utf-8')
    replacements = [(json.loads(a), json.loads(b)) for a, b in re.findall(
        r'\.replace\(("(?:[^"\\]|\\.)*"),\s*("(?:[^"\\]|\\.)*")\)', source)]
    if not replacements:
        raise ValueError('Cannot read CardNameUtil transliterations')

    def normalize(name):
        for old, new in replacements:
            name = name.replace(old, new)
        return name.lower()

    return normalize


def source_inventory(root, normalize):
    sets = {}
    registered = collections.defaultdict(set)
    declaration = re.compile(r'super\("(?:[^"\\]|\\.)*",\s*"([^"]+)".*?SetType\.(\w+)', re.S)
    strings_and_comments = re.compile(r'"(?:[^"\\]|\\.)*"|//[^\n]*|/\*.*?\*/', re.S)
    for path in sorted((root / 'Mage.Sets/src/mage/sets').glob('*.java')):
        source = path.read_text(encoding='utf-8')
        source = strings_and_comments.sub(lambda m: m[0] if m[0].startswith('"') else ' ', source)
        match = declaration.search(source)
        if not match:
            raise ValueError('Cannot identify set: ' + str(path))
        sets[match[1]] = match[2]
        for name in re.findall(r'new SetCardInfo\(("(?:[^"\\]|\\.)*")\s*,', source):
            registered[normalize(json.loads(name))].add(path.relative_to(root).as_posix())
    classes = collections.defaultdict(set)
    for path in (root / 'Mage.Sets/src/mage/cards').rglob('*.java'):
        key = re.sub('[^a-z0-9]', '', normalize(path.stem))
        classes[key].add(path.relative_to(root).as_posix())
    return sets, registered, classes


def audit(repository, root, baseline):
    if (len(baseline) != EXPECTED_AUDIT_ENTRIES
            or len({row['arena_name'] for row in baseline}) != EXPECTED_AUDIT_ENTRIES):
        raise ValueError(f'Expected the {EXPECTED_AUDIT_ENTRIES} distinct original audit entries')
    normalize = name_normalizer(root)
    sets, registered, classes = source_inventory(root, normalize)
    with repository.open(encoding='utf-8-sig', newline='') as source:
        rows = list(csv.DictReader(source))
    if len(rows) < MIN_REPOSITORY_RECORDS:
        raise ValueError('Incomplete repository export')
    catalog = {}
    for line in (root / 'Mage/src/main/resources/arena-creature-pool.tsv').read_text(encoding='utf-8').splitlines():
        if line and not line.startswith('#'):
            mv, name = line.split('\t')
            key = normalize(name)
            if key in catalog:
                raise ValueError('Ambiguous catalog name: ' + name)
            catalog[key] = (name, int(mv))
    names = collections.defaultdict(list)
    faces = collections.defaultdict(list)
    selected = {}
    for row in rows:
        key = normalize(row['NAME'])
        names[key].append(row)
        for field in FACE_FIELDS:
            if row[field]:
                faces[normalize(row[field])].append(row)
        if (row['NIGHTCARD'] != 'TRUE' and row['SPLITCARDHALF'] != 'TRUE'
                and 'CREATURE' in row['TYPES'] and sets.get(row['SETCODE']) is not None
                and (sets[row['SETCODE']] != 'CUSTOM_SET' or row['SETCODE'] == 'ATC')
                and key in catalog and int(row['MANAVALUE']) == catalog[key][1]):
            selected.setdefault(key, []).append(row)

    results = []
    for previous in baseline:
        name = previous['arena_name']
        key = normalize(name)
        if key in catalog and int(previous['mana_value']) != catalog[key][1]:
            raise ValueError('Audit baseline mana value changed: ' + name)
        matches = selected.get(key, ())
        if matches:
            if previous.get('result') in ('unimplemented', 'implemented_since_audit'):
                reason = 'implemented_since_audit'
                evidence = 'Implementation now registered; selectable'
            else:
                reason = 'fixed_name_spelling' if all(row['NAME'] != name for row in matches) else 'fixed_set_filter'
                evidence = 'Canonical identity and mana value match; selectable'
        elif key not in catalog and any(row['NIGHTCARD'] == 'TRUE' for row in names.get(key, ())):
            reason = 'removed_meld_result'
            matches = names[key]
            evidence = 'Meld back face; alternate-printing related-part IDs caused false inclusion'
        else:
            class_key = re.sub('[^a-z0-9]', '', key)
            if names.get(key) or faces.get(key) or registered.get(key) or classes.get(class_key):
                raise ValueError('Unresolved implementation/face candidate: ' + name)
            reason = 'unimplemented'
            evidence = 'No matching repository name/face, source registration or class'
        results.append(dict(arena_name=name, mana_value=previous['mana_value'], result=reason,
                            xmage_name='; '.join(sorted({r['NAME'] for r in matches})),
                            set_codes='; '.join(sorted({r['SETCODE'] for r in matches})),
                            classes='; '.join(sorted({r['CLASSNAME'] for r in matches})), evidence=evidence))
    # Check every currently missing name, including entries outside the baseline.
    missing = {name for key, (name, mv) in catalog.items() if key not in selected}
    recorded_missing = {row['arena_name'] for row in results if row['result'] == 'unimplemented'}
    if missing != recorded_missing:
        raise ValueError('Current missing set differs from the audit baseline: ' + repr(sorted(missing ^ recorded_missing)))
    stream = io.StringIO(newline='')
    writer = csv.DictWriter(stream, fieldnames=('arena_name', 'mana_value', 'result', 'xmage_name',
                                              'set_codes', 'classes', 'evidence'), delimiter='\t', lineterminator='\n')
    writer.writeheader()
    writer.writerows(results)
    print('Audited:', len(results), dict(collections.Counter(row['result'] for row in results)))
    print('Supported:', len(selected), '/', len(catalog))
    return stream.getvalue()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('repository_csv', type=pathlib.Path)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    root = pathlib.Path(__file__).resolve().parent.parent
    report = root / REPORT
    with report.open(encoding='utf-8', newline='') as source:
        baseline = list(csv.DictReader(source, delimiter='\t'))
    content = audit(args.repository_csv, root, baseline)
    if args.check:
        if report.read_text(encoding='utf-8') != content:
            raise ValueError('Audit report differs: ' + str(report))
    else:
        write_text_atomically(report, content)
