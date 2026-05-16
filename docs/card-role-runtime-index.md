# Card Role Runtime Index

`Mage/src/main/resources/card-roles/scryfall-card-roles-runtime.json.gz` is an optional runtime hint index for AI deck profiling.

The AI first checks the generated index for roles and mechanics by print key, name, and oracle id. If a card is missing from the index, `DeckProfileService` falls back to rules-text and ability-based classification. A missing resource is logged as a warning because packaged builds are expected to include the committed artifact.

## Regenerate

The generator is local-cache first. It expects the large Scryfall `all_cards` bulk JSON at:

```text
.research/scryfall-backfill/raw/scryfall-all_cards.json
```

That raw file is intentionally ignored by Git and is currently about 2.5 GB.

To rebuild from an existing local cache:

```bash
node tools/card-roles/build-scryfall-card-roles.js
```

To download the raw Scryfall bulk file when it is missing:

```bash
node tools/card-roles/build-scryfall-card-roles.js --download
```

To refresh the raw Scryfall bulk file before rebuilding:

```bash
node tools/card-roles/build-scryfall-card-roles.js --refresh-raw
```

The committed runtime artifact is written to:

```text
Mage/src/main/resources/card-roles/scryfall-card-roles-runtime.json.gz
```

Research outputs, including the full role data, summary, and Markdown report, are written under:

```text
.research/scryfall-backfill/roles
```
