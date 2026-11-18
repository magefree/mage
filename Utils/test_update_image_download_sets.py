"""Offline regressions: python -m unittest discover -s Utils -p test_update_image_download_sets.py"""

import gzip
import hashlib
import json
import pathlib
import tempfile
import unittest
from unittest import mock

import update_image_download_sets as generator


class ImageDownloadInventoryTest(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = pathlib.Path(self.temporary.name)
        self.cards = self.root / 'cards.jsonl'
        self.metadata = self.root / 'metadata.json'
        self.snapshot = {'type': 'all_cards', 'updated_at': '2026-10-15T09:18:36+00:00',
                         'jsonl_download_uri': 'https://data.scryfall.io/all-cards/example.jsonl.gz'}
        self.metadata.write_text(json.dumps(self.snapshot), encoding='utf-8')
        # Deliberately small, independently specified examples of the source fields.
        rows = [self.card('pap', ['paper'], 'legal'),
                self.card('res', ['mtgo'], 'restricted'),
                self.card('ban', ['paper'], 'banned'),
                self.card('mix', ['arena'], 'not_legal'),
                self.card('mix', ['paper'], 'legal'),
                self.card('mix', ['paper'], 'not_legal'),
                self.card('dig', ['arena'], 'not_legal', lang='ja'),
                self.card('ren', ['paper'], 'legal', lang='fr'),
                self.card('jok', ['paper'], 'not_legal'),
                self.card('cod', ['paper'], 'legal', arena_id=123)]
        self.cards.write_bytes(b''.join((json.dumps(row) + '\n').encode('utf-8') for row in rows))
        self.expansions = self.root / 'Mage.Sets/src/mage/sets'
        self.expansions.mkdir(parents=True)
        for code in ('PAP', 'RES', 'BAN', 'MIX', 'DIG', 'REN', 'JOK', 'COD', 'ABSENT'):
            (self.expansions / (code + '.java')).write_text(
                'super("Example", "' + code + '", date, SetType.EXPANSION);', encoding='utf-8')
        self.production = self.root / ('Mage.Client/src/main/resources/'
                                      'org/mage/plugins/card/images/download-format-sets.properties')
        self.fixture = self.root / ('Mage.Client/src/test/resources/'
                                   'org/mage/plugins/card/images/download-format-coverage.csv')

    @staticmethod
    def card(code, games, vintage, **extra):
        return dict(set=code, games=games, legalities={'vintage': vintage}, **extra)

    def generate(self, check=False):
        # The CLI keeps its full-snapshot safeguards; only these tiny fixtures bypass the limits.
        with mock.patch.multiple(generator, MIN_PRINTINGS=1, MIN_SETS=1):
            generator.update(self.cards, self.root, self.metadata, check)

    def test_printing_fields_cover_partial_mixed_banned_and_foreign_only_sets(self):
        counts, digest, records = generator.read_printings(self.cards)
        self.assertEqual({'PAP': [0, 1], 'RES': [0, 1], 'BAN': [0, 1], 'MIX': [1, 1],
                          'DIG': [1, 0], 'REN': [0, 1], 'JOK': [0, 0], 'COD': [0, 1]}, counts)
        self.assertEqual(10, records)
        self.assertEqual(hashlib.sha256(self.cards.read_bytes()).hexdigest(), digest)
        self.generate()
        entries = [line for line in self.production.read_text().splitlines() if not line.startswith('#')]
        self.assertEqual(['ABSENT=', 'BAN=V', 'COD=V', 'DIG=H', 'JOK=', 'MIX=HV',
                          'PAP=V', 'REN=V', 'RES=V'], entries)

    def test_plain_and_gzip_streams_have_identical_counts_and_uncompressed_hash(self):
        compressed = self.root / 'cards.jsonl.gz'
        compressed.write_bytes(gzip.compress(self.cards.read_bytes()))
        self.assertEqual(generator.read_printings(self.cards), generator.read_printings(compressed))
        self.generate()
        self.cards = compressed
        self.snapshot['compressed_size'] = compressed.stat().st_size
        self.metadata.write_text(json.dumps(self.snapshot), encoding='utf-8')
        self.generate(check=True)

    def test_missing_or_unknown_source_fields_cannot_silently_exclude_sets(self):
        for card in ({'set': 'bad', 'legalities': {'vintage': 'legal'}},
                     {'set': 'bad', 'games': ['paper']},
                     self.card('bad', ['paper'], 'unexpected')):
            with self.subTest(card=card):
                self.cards.write_text(json.dumps(card) + '\n', encoding='utf-8')
                with self.assertRaises((KeyError, ValueError)):
                    self.generate()
                self.assertFalse(self.production.exists())
                self.assertFalse(self.fixture.exists())

    def test_generation_is_reproducible_and_preserves_snapshot_provenance(self):
        self.generate()
        expected = (self.production.read_bytes(), self.fixture.read_bytes())
        self.generate()
        self.assertEqual(expected, (self.production.read_bytes(), self.fixture.read_bytes()))
        self.assertNotIn(b'\r', expected[0])
        self.assertIn(b'# Snapshot updated: 2026-10-15T09:18:36+00:00\n', expected[0])
        self.assertIn(self.snapshot['jsonl_download_uri'].encode('utf-8'), expected[1])

    def test_check_detects_a_stale_fixture_without_writing_either_file(self):
        self.generate()
        self.generate(check=True)
        self.fixture.write_text('stale fixture\n', encoding='utf-8')
        before = (self.production.read_bytes(), self.fixture.read_bytes())
        with self.assertRaisesRegex(ValueError, 'Generated inventory differs'):
            self.generate(check=True)
        self.assertEqual(before, (self.production.read_bytes(), self.fixture.read_bytes()))

    def test_incomplete_input_leaves_existing_inventories_untouched(self):
        self.generate()
        before = (self.production.read_bytes(), self.fixture.read_bytes())
        with self.assertRaisesRegex(ValueError, 'Incomplete input'):
            generator.update(self.cards, self.root, self.metadata)
        self.assertEqual(before, (self.production.read_bytes(), self.fixture.read_bytes()))

    def test_mismatched_compressed_snapshot_is_rejected_before_writing(self):
        self.cards = self.root / 'cards.jsonl.gz'
        self.cards.write_bytes(gzip.compress(b'{}\n'))
        self.snapshot['compressed_size'] = self.cards.stat().st_size + 1
        self.metadata.write_text(json.dumps(self.snapshot), encoding='utf-8')
        with self.assertRaisesRegex(ValueError, 'Compressed input size differs'):
            self.generate()
        self.assertFalse(self.production.exists())

    def test_metadata_for_another_bulk_type_is_rejected(self):
        self.snapshot['type'] = 'oracle_cards'
        self.metadata.write_text(json.dumps(self.snapshot), encoding='utf-8')
        with self.assertRaisesRegex(ValueError, 'Expected Scryfall all_cards metadata'):
            self.generate()
        self.assertFalse(self.production.exists())

    def test_duplicate_or_unrecognized_expansion_declarations_are_rejected(self):
        source = self.expansions / 'Extra.java'
        for content in ('super("Duplicate", "PAP", date, SetType.CORE);', 'unrecognized declaration'):
            with self.subTest(content=content):
                source.write_text(content, encoding='utf-8')
                with self.assertRaises(ValueError):
                    self.generate()
                self.assertFalse(self.production.exists())


if __name__ == '__main__':
    unittest.main()
