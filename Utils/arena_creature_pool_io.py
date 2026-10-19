"""File handling shared by the offline Arena creature maintenance tools."""

import hashlib
import pathlib
import tempfile


def sha256_file(path):
    digest = hashlib.sha256()
    with path.open('rb') as source:
        for chunk in iter(lambda: source.read(1024 * 1024), b''):
            digest.update(chunk)
    return digest.hexdigest()


def write_text_atomically(path, content):
    # Keep the temporary file on the same filesystem, and close it before
    # replacement so this also works on Windows. A failed write preserves the
    # previous catalog/report and removes the temporary file.
    temporary = None
    try:
        with tempfile.NamedTemporaryFile(mode='w', encoding='utf-8', newline='\n',
                                         dir=path.parent, prefix=path.name + '.',
                                         suffix='.tmp', delete=False) as target:
            temporary = pathlib.Path(target.name)
            target.write(content)
        temporary.replace(path)
    finally:
        if temporary is not None:
            temporary.unlink(missing_ok=True)
