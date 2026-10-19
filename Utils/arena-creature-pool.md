# Arena random creature selection

`Pool of Vigorous Growth` uses `RandomCardUtil.createRandomCreature`.
Random-creature conjure effects can reuse the same selector.

The selector combines automatic full-set availability with a checked-in legacy
catalog. Adding a normal card class and set registration is enough: card authors
do not update a second allowlist for modern Standard or Arena sets.

## Automatic full-set availability

`ExpansionSet.getArenaReleaseDate()` identifies full Arena releases. By default,
it uses the existing set release date for `MAGIC_ARENA` sets and Standard set
types (`EXPANSION`, `CORE`, `SUPPLEMENTAL_STANDARD_LEGAL`) from Ixalan onward
(2017-09-29). This is permanent availability, independent of rotation or bans.
An implemented, front-face creature in a released full set is eligible even when
its name is absent from the legacy catalog. Aliases and reprints still receive
only one outcome; back faces, split halves and A- rebalanced variants are excluded.

The repository inventory is loaded once after normal card scanning at server
startup. Name matching and set metadata are resolved once, keeping the earliest
availability of each creature identity. The immutable pool is cached until the
next launch that adds a candidate, then rebuilt from that inventory on the next
request. Later reprints do not add outcomes or trigger cache rebuilds. Already
loaded future sets become available without a restart, database query or network
request. New implementations are picked up with the normal deployment/startup
that loads their classes.

For unusual launches, a set maintainer can set `arenaReleaseDate` once in the set
constructor or override `getArenaReleaseDate()`; returning null disables automatic
full-set inclusion. No per-card change is required. Explicit dates are supplied
for Khans of Tarkir, Modern Horizons 3, The Lord of the Rings, and Spider-Man.
Default release dates are XMage's recorded dates (often tabletop dates), so a
different Arena launch needs that set-level override rather than a guessed offset.

Commander products, partial reissues, pre-Arena sets, and other supplemental sets
do not become fully available merely because a few of their cards are on Arena.
They continue to use card-level evidence from the catalog. New fully ported
supplemental sets can opt in through the same set-level launch metadata. Unusual
releases and partial imports still need maintained availability data; routine
creature implementations in full Arena sets require no extra work.

## Legacy and partial-release catalog

The checked-in `Mage/src/main/resources/arena-creature-pool.tsv` lists individual
creature identities and mana values. It combines primary, non-token, original-rule
creatures from a read-only installed Arena card database with newer Arena
printings from Scryfall's `all_cards` JSONL snapshot. The header records versions,
checksums, dates and counts. The installed client snapshot predates the Scryfall
snapshot; neither is a live server query.

This includes digital, banned, event-only and conjure-only cards, independent of
format, collection ownership, rarity, color identity or which expansion XMage
uses to implement a card. Entire sets are **not** treated as available just
because Arena has some cards from them. Back faces, meld results, tokens and
alternate/rebalanced versions do not add separate outcomes. X is zero when
determining a card's mana value outside the stack.

XMage combines supported catalog entries with released full-set creatures.
Each supported name has equal probability, independent
of reprints. Missing mana values produce nothing; they never fall back to paper
cards or a placeholder creature. AI simulation uses the same cached pool.
Matching uses `CardNameUtil`'s explicit ASCII transliterations and ignores case,
while retaining punctuation and checking mana value. Catalog spellings remain
the canonical identities; ambiguous normalized entries are rejected. A card's
printing need not be tagged with an Arena set type: MB2's normal cards and ATC's
official tutorial implementations are usable when the catalog permits them.
Other custom-set implementations remain excluded.

## Limits of parity

Arena's client database establishes client availability, **not** the server's
complete eligibility algorithm. Wizards has not published a precise eligibility
or probability specification for these effects. In particular, this catalog may
include preloaded cards that the server excludes. Equal weighting by name is an
explicit XMage policy, not a verified claim about Arena's RNG. Current rebalanced
rules and cards XMage has not implemented cannot be reproduced by this selector.
`RandomCardUtil.getUnimplementedArenaCreatureNames()` reports gaps in the legacy catalog;
selection renormalizes over supported originals. Thus this is a versioned,
availability-based approximation, not a guarantee of exact live Arena parity.

## Refresh and verify

Run `python Utils/update_arena_creature_pool.py ARENA_DATABASE ALL_CARDS_JSONL
--metadata SCRYFALL_METADATA_JSON` from the checkout, with paths on one command
line. The Arena source is `MTGA_Data/Downloads/Raw/Raw_CardDatabase_*.mtga` from an
installed client. The Scryfall metadata comes from
<https://api.scryfall.com/bulk-data/all-cards>; use its `jsonl_download_uri`.
No network access is performed by the generator. `--check` verifies the generated
file without changing it. Truncated inputs and conflicting mana values fail
before writing. Checksums stream their source files, and catalog/report updates
replace the previous file atomically after a successful write. Review both
snapshots when refreshing, especially if one is older than the other. Run
`python -m unittest discover -s Utils -p 'test_*arena_creature*.py'` and the Java
selector/gameplay tests.

Wizards' August 2025 patch notes confirm that "duplicate of" was removed from
random-creature conjure wording and that some conjurable cards cannot be put in
decks. This is why deck legality is not used as an availability filter:
<https://mtgarena-support.wizards.com/hc/en-us/articles/40418736650132-Patch-Notes-2025-51-00>.
