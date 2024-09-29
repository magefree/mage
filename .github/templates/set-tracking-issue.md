This checklist is here to help manage the implementation of {{setName}}. If a card is marked as being in progress then someone is working on it.

If you're new to implementing cards then you likely don't have permission to check things off. This is totally fine! We still appreciate your contributions so just leave a comment to let us know that you're working on it.

Don't worry about moving things from in progress to completed either, this issue is automatically updated by github actions, and don't worry about fixing text issues as those are usually handled when the set is done.

[All Sets](https://github.com/magefree/mage/wiki/Set-implementation-list)


{{#hasUnimplementedCards}}
# Unimplemented Cards

{{#unimplementedCards}}
- [{{#pr}}x{{/pr}}{{^pr}} {{/pr}}]  In progress -- [{{name}}]({{scryfall}})
{{/unimplementedCards}}

[Scryfall gallery of everything currently unimplemented]({{unimplementedScryfallLink}})
{{/hasUnimplementedCards}}

{{^hasUnimplementedCards}}
All cards are implemented
{{/hasUnimplementedCards}}

# Implemented Cards
<details>
  <summary>Click to expand</summary>
  </br>

  {{#hasImplementedCards}}
  {{#implementedCards}}
  - [{{#pr}}x{{/pr}}{{^pr}} {{/pr}}]  Done -- [{{name}}]({{scryfall}})
  {{/implementedCards}}
  {{/hasImplementedCards}}

</details>

