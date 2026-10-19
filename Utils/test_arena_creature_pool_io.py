import hashlib
import pathlib
import tempfile
import unittest
from unittest import mock

from arena_creature_pool_io import sha256_file, write_text_atomically


class ArenaCreaturePoolIoTest(unittest.TestCase):
    def test_hash_spans_multiple_chunks(self):
        content = bytes(range(256)) * 8193
        with tempfile.TemporaryDirectory() as directory:
            path = pathlib.Path(directory) / 'source'
            path.write_bytes(content)
            self.assertEqual(sha256_file(path), hashlib.sha256(content).hexdigest())

    def test_atomic_write_preserves_utf8_and_newlines(self):
        with tempfile.TemporaryDirectory() as directory:
            path = pathlib.Path(directory) / 'catalog.tsv'
            path.write_text('previous catalog', encoding='utf-8')
            content = '2\tÉowyn\n3\tExample\n'
            write_text_atomically(path, content)
            self.assertEqual(path.read_bytes(), content.encode('utf-8'))
            self.assertEqual(list(path.parent.iterdir()), [path])

    def test_failed_replacement_preserves_original_and_cleans_up(self):
        with tempfile.TemporaryDirectory() as directory:
            path = pathlib.Path(directory) / 'catalog.tsv'
            path.write_bytes(b'previous catalog')
            with mock.patch.object(pathlib.Path, 'replace', side_effect=OSError('replacement failed')):
                with self.assertRaisesRegex(OSError, 'replacement failed'):
                    write_text_atomically(path, 'replacement catalog')
            self.assertEqual(path.read_bytes(), b'previous catalog')
            self.assertEqual(list(path.parent.iterdir()), [path])


if __name__ == '__main__':
    unittest.main()
