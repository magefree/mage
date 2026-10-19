import json
from contextlib import closing
import pathlib
import sqlite3
import tempfile
import unittest

import update_arena_creature_pool as pool


class ArenaCreaturePoolTest(unittest.TestCase):
    def test_client_event_cards_and_primary_faces(self):
        with tempfile.TemporaryDirectory() as directory:
            path = pathlib.Path(directory) / 'cards.mtga'
            with closing(sqlite3.connect(path)) as db, db:
                db.executescript('''CREATE TABLE Versions (Type TEXT, Version TEXT);
                    INSERT INTO Versions VALUES ('Data','test'),('GRP','test');
                    CREATE TABLE Localizations_enUS (LocId INTEGER, Formatted INTEGER, Loc TEXT);
                    CREATE TABLE Cards (TitleId INTEGER, Order_CMCWithXLast INTEGER,
                        IsToken INTEGER, IsPrimaryCard INTEGER, IsRebalanced INTEGER, Types TEXT);''')
                for i, (name, cmc, token, primary, rebalanced, types) in enumerate([
                    ('Bringer of the Blue Dawn', 9, 0, 1, 0, '2'),
                    ('Bringer of the Blue Dawn', 9, 0, 1, 0, '2'),  # Reprint
                    ('Stonecoil Serpent', 1000, 0, 1, 0, '1,2'),  # X is zero
                    ('Abigale, Eloquent <nobr>First-Year</nobr>', 2, 0, 1, 0, '2'),
                    ('Zombie', 0, 1, 1, 0, '2'),
                    ('Insectile Aberration', 1, 0, 0, 0, '2'),
                    ('A-Hobbling Zombie', 3, 0, 0, 1, '2'),
                    ('Wrong type', 1, 0, 1, 0, '12'),
                ]):
                    db.execute('INSERT INTO Localizations_enUS VALUES (?,1,?)', (i, name))
                    db.execute('INSERT INTO Cards VALUES (?,?,?,?,?,?)', (i, cmc, token, primary, rebalanced, types))
            before = path.read_bytes()
            cards, versions, digest = pool.read_arena(path)
            self.assertEqual(cards, {'Bringer of the Blue Dawn': 9, 'Stonecoil Serpent': 0,
                                     'Abigale, Eloquent First-Year': 2})
            self.assertEqual(versions['Data'], 'test')
            self.assertEqual(len(digest), 64)
            self.assertEqual(before, path.read_bytes())

    def test_printing_availability_not_set_legality_or_arena_id(self):
        def printing(name, games, **extra):
            result = dict(id=name, name=name, cmc=2, games=games, type_line='Creature — Bear',
                          set='mixed', arena_id=None, legalities={'historic': 'banned'})
            result.update(extra)
            return result

        records = [
            printing('Banned but available', ['arena']),
            printing('Banned but available', ['arena']),
            printing('Paper card in same set', ['paper']),
            printing('Digital only', ['arena'], digital=True),
            printing('A-Digital only', ['arena']),
            printing('Zombie', ['arena'], layout='token'),
            printing('Front // Back', ['arena'], card_faces=[
                dict(name='Front', type_line='Creature — Human'),
                dict(name='Back', type_line='Creature — Insect')]),
            printing('Spell // Creature', ['arena'], card_faces=[
                dict(name='Spell', type_line='Sorcery'),
                dict(name='Creature', type_line='Creature — Human')]),
            printing('Meld result', ['arena'], all_parts=[dict(id='Meld result', component='meld_result')]),
            printing('Alternate-art meld result', ['arena'], layout='meld', all_parts=[
                dict(id='regular-printing-id', name='Alternate-art meld result', component='meld_result')]),
            printing('Meld front', ['arena'], layout='meld', all_parts=[
                dict(id='another-id', name='Meld result', component='meld_result')]),
        ]
        with tempfile.TemporaryDirectory() as directory:
            path = pathlib.Path(directory) / 'cards.jsonl'
            path.write_text(''.join(json.dumps(row) + '\n' for row in records), encoding='utf-8')
            cards, digest, count = pool.read_scryfall(path)
        self.assertEqual(cards, {'Banned but available': 2, 'Digital only': 2, 'Front': 2, 'Meld front': 2})
        self.assertEqual(count, len(records))
        self.assertEqual(len(digest), 64)

    def test_conflicting_mana_values_fail(self):
        cards = {'Example': 2}
        with self.assertRaisesRegex(ValueError, 'Conflicting mana values'):
            pool.add(cards, 'Example', 3)

    def test_sources_with_different_capitalization_have_one_outcome(self):
        self.assertEqual({"Sol'kanar the Tainted": 5}, pool.merge_catalogs(
            {"Sol'Kanar the Tainted": 5}, {"Sol'kanar the Tainted": 5}))
        with self.assertRaisesRegex(ValueError, 'Conflicting mana values'):
            pool.merge_catalogs({'Example': 2}, {'EXAMPLE': 3})

    def test_bad_metadata_preserves_existing_catalog(self):
        with tempfile.TemporaryDirectory() as directory:
            root = pathlib.Path(directory)
            target = root / pool.RESOURCE
            target.parent.mkdir(parents=True)
            target.write_text('existing catalog', encoding='utf-8')
            metadata = root / 'metadata.json'
            metadata.write_text('{}', encoding='utf-8')
            with self.assertRaisesRegex(ValueError, 'snapshot metadata'):
                pool.update(root / 'absent.db', root / 'absent.jsonl', metadata, root)
            self.assertEqual(target.read_text(encoding='utf-8'), 'existing catalog')


if __name__ == '__main__':
    unittest.main()
