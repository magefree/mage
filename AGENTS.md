# Agent guidance

XMage is a Java and Maven project for playing Magic: The Gathering. See the
[README](readme.md) for project information and developer documentation.

## Repository map

- `Mage/`: game engine, rules, abilities, and effects.
- `Mage.Sets/`: card implementations and set registrations.
- `Mage.Tests/`: game and card tests.
- `Mage.Verify/`: card and set data verification (`make test-verify-cards`).
- `Mage.Client/` and `Mage.Server/`: client and server applications.
- `Mage.Plugins/`: client plugins, including the game counter.
- `Mage.Server.Plugins/`: game modes, deck formats, players, and tournaments.

## Working in the repository

- Follow existing project conventions and reuse established engine behavior where possible.
- Keep Java changes compatible with the Java 8 target configured in `pom.xml`.
- Keep changes focused on the requested task.

## Validation

- Run tests appropriate to the change. `make test` runs the Maven test suite;
  CI's build and test command is in `.github/workflows/maven.yml`.
- Report the checks run and any relevant checks that could not be run.

## Task-specific guidance

Reusable task workflows live in `.agents/skills/`. Read the matching skill for
task-specific guidance:

- Implementing a card: `.agents/skills/implement-card/SKILL.md`.
