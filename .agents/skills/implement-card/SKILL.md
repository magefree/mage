---
name: implement-card
description: Implement a Magic card in XMage or add a card printing to a set. Use when asked to add a card or implement its behavior.
---

# Implement a card

1. Identify the requested card and set. For official cards, consult Scryfall
   when reachable for current Oracle text, characteristics, and printing
   details; `Utils/mtg-cards-data.txt` provides another view. For custom cards,
   use the supplied source. Flag information that cannot be verified.
2. Look for an existing card implementation in `Mage.Sets/src/mage/cards/`
   before creating one. Search for similar cards and reusable engine abilities,
   effects, costs, and targets in `Mage/`.
3. Implement the card using established patterns and Magic's Comprehensive
   Rules. Check behavior and generated text against the card's current Oracle
   text or supplied custom-card text, including relevant targeting, zones,
   ownership, multiplayer interactions, and AI choices.
4. When registration is in scope, add all printings of the card in the set to
   `Mage.Sets/src/mage/sets/`, with the correct collector number and rarity
   for each. Choose the appropriate art option for each printing, including
   its frame treatment and whether multiple printings use various art; see the
   constants in `Mage/src/main/java/mage/cards/ExpansionSet.java`. If an option
   cannot be determined confidently, flag the printing for human review and
   finalization rather than guessing. Preserve existing registrations and the
   set's ordering conventions.
5. Validate the changed behavior with focused checks appropriate to the task.
   For simple uses of existing tested abilities or effects, rely on their
   existing tests instead of duplicating them. Add tests for custom abilities
   or effects. Use `Mage.Verify` when useful for card, set, or generated-text
   checks. Respect the requested testing scope and report what was and was not
   verified.

## Card coding style

- Include an `@author` Javadoc tag on the card class, using the contributor's
  chosen name or handle.
- Put the Oracle text of each ability in a comment directly above its
  implementation.
- Import classes and use simple names in card implementation code; avoid
  fully qualified class names outside imports. Set-registration files follow
  their existing conventions.
- Use established effects and abilities where possible.
- Prefer the shortest overload that provides the intended behavior; check its
  defaults before omitting arguments.
- Do not use `InfoEffect` as a substitute for required game behavior. Flag any
  behavior that cannot be implemented.
- Prefer an existing `StaticFilters` filter when it matches the intended rules
  and text.

## Commit message

When committing a card implementation, use `[SET] Implement Card Name`, where
`SET` is the full code of the set that introduced the card.

## Pull requests

Typically, use one branch and one pull request per card. Link the set's
implementation tracking issue in the pull request description.
