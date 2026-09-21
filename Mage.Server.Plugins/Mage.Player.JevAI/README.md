# JevAI

Two AI players built on [Jev](https://docs.typesafe.ai), TypeSafe's System One model,
reached through [OpenRouter](https://openrouter.ai/~typesafe/jev-latest).

| Player type in the server | Class | What it is |
| --- | --- | --- |
| `Computer - JevAI` | `JevPlayer` | Every decision is a question to Jev. No search at all. |
| `Computer - JevAI hybrid` | `JevHybridPlayer` | The stock XMage bot's search, with Jev choosing what to search and which searched line to play. |

Both fall back to their base class when no key is configured or a call fails, so a
game never stalls on the network.

## Configuration

Set the OpenRouter key in the environment the server runs in:

```sh
export OPENROUTER_API_KEY=sk-or-...    # required, https://openrouter.ai/keys
export JEV_MODEL=~typesafe/jev-latest  # optional
export JEV_ENDPOINT=https://openrouter.ai/api/alpha/decisions  # optional
export JEV_TIMEOUT_MS=8000             # optional
```

System properties work too (`-Dopenrouter.api.key=...`).

Both names are also entries in the `PlayerType` enum (`Mage/src/main/java/mage/players/PlayerType.java`).
The server matches a `config.xml` player type to that enum by its description and throws on an
unknown one, so a bot added to the config alone stops the server from starting.

## Running it with the UI

From a clean checkout:

```sh
mvn install -DskipTests            # builds server, client and this plugin
```

Then start the server with the key in its environment, and the client separately:

```sh
# server (working directory must be Mage.Server, it reads config/config.xml from there)
cd Mage.Server
OPENROUTER_API_KEY=sk-or-... mvn exec:java -Dexec.mainClass=mage.server.Main

# client, in another shell
cd Mage.Client
mvn exec:java -Dexec.mainClass=mage.client.MageFrame
```

On Java 9 or newer - this was tested on Java 25 - both processes need the JDK opened up
for JBoss Remoting, or the client connects and then dies serializing the first message
with `InaccessibleObjectException: ... module java.base does not "opens java.io"`:

```
--add-opens java.base/java.io=ALL-UNNAMED
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
--add-opens java.base/java.net=ALL-UNNAMED
```

In the client: connect to `localhost:17171` (any user name, registration is off in the dev
config), create a table, and pick **Computer - JevAI** or **Computer - JevAI hybrid** as the
opponent's player type. The server log prints `Loaded game types: ..., players: 6` when both
bots registered.

The key has to be in the *server's* environment: the bots run server side, the client never
talks to OpenRouter.

Cards are drawn without art until the images are fetched: in the client, the **Download**
button on the toolbar offers *Download card images* and *Download mana symbols*. They land
in `plugins/images/` under the client's working directory, and the client has to be
restarted to pick them up. This is a client feature and has nothing to do with the bots.

## JevPlayer - the pure one

Extends `ComputerPlayer`, which on its own does nothing but pass. Code collects the
legal options and describes the game; Jev picks.

| Decision | Question type |
| --- | --- |
| Mulligan | noul |
| Which action to take on priority (or pass) | choice |
| Where each creature attacks, or holds back | choice per creature, batched in one call |
| Which attacker each creature blocks | choice per creature, batched in one call |
| Yes/no prompts | noul, with the threshold set by the engine's `Outcome` |
| Each target of a spell or ability | choice per target |
| Value dialogs, modes, trigger order, amounts | choice |

Paying mana stays in code: while `payManaMode` is set, every dialog goes to
`ComputerPlayer`, which derives the answer from the unpaid cost. Asking the model
there wastes a call and can pick a color that cannot pay.

Two things the model cannot infer from rules text, so code supplies them as facts:

- **Symmetry.** A sweeper or a sacrifice cost hits you too. Options carry a count of
  both boards.
- **Whose loss it is.** When every candidate for a "bad" effect belongs to you, it is
  a discard or a sacrifice, not a target: the question says to give up what you can
  most afford to lose.

## JevHybridPlayer - search plus judgement

Extends `ComputerPlayer7`, the bot the server ships as `Computer - mad`. The search
plays lines out and scores the resulting board; Jev handles the two things a board
score cannot.

1. **Before the search** (`optimize`), when the root has more than 5 candidate plays,
   one noul per play asks whether it is worth simulating. Plays below the bar are
   dropped, never below 3 remaining. Fewer branches, same time budget, deeper search
   on what is left. Only the root's call is asked: the search visits thousands of
   nodes, and the flag that marks the root is `volatile` because `addActionsTimed`
   runs the search on a pool thread.
2. **After the search** (`calculateActions`), the top lines come back with scores.
   Jev picks among them, seeing each line's plays and its score. Lines whose score
   is an alpha-beta sentinel are not offered - those numbers are not comparable.

Targeting, combat and mana stay with the search.

## Measured strength

Against `Computer - mad`, the bot the server ships, both sides on `Power Hungry.dck`,
fixed shuffle seeds, games cut off at turn 30:

| Player | Won-lost-drawn | Games |
| --- | --- | --- |
| `JevPlayer` (pure) | 2-7-1 | 10 |
| `JevHybridPlayer` | 6-3-1 | 10 |

Over every game run on the final code: pure 4-10-2 in 16, hybrid 11-6-3 in 20. A draw
means the turn cap was reached, not a rules draw; every decided game ended with a
player at 0 life or less.

The gap is not surprising. The hybrid inherits the search, so combat maths, targeting
and mana are already right and its floor is the stock bot's strength. The pure player
derives everything from a text description and never simulates: it cannot tell whether
a creature survives a block, and its losses look the same every time - it ends with an
empty board while the opponent has five to twelve creatures.

### What the model is good for here

Pruning candidate plays *before* the search measured worse than not pruning:

| Hybrid configuration | Won-lost-drawn (10 games) |
| --- | --- |
| Jev prunes, then the search runs | 3-6-1 |
| The search runs, then Jev picks | 5-3-2 |

Judging a play before the search has said where it leads throws away lines the search
would have ranked properly. Pruning is therefore off by default
(`-Djev.hybrid.prune=true` re-enables it). Choosing among lines the search scored
within a few points of each other is the opposite case: there the score genuinely
cannot separate them, and 56 of 103 such calls in one series took a line other than
the top-scored one.

## Cost and latency

Every decision is an HTTP call. The pure player makes one per decision, batching
attack and block questions - roughly 30-200 per game. The hybrid makes about half
that, but each search is slower than a single question.

Measured over real games, one call takes a median of 540 ms and a 90th percentile of
730 ms, carrying about 3.3 KB of state and 550-1100 input tokens. A call costs
$0.00002-0.00005, so a game costs $0.01-0.02.

Where that time goes differs by player. The pure one made 31 calls in a 32 s game:
18 s of it, over half the game, was spent waiting on the model. The hybrid made 8
calls in a 49 s game, and its time goes mostly to the search, which on a large board
runs for minutes.

One call in that hybrid game took 20 s and hit the timeout. Nothing broke - the
fallback took over - but since the fallback is free, waiting that long buys nothing,
which is why the default timeout is 8 s.

## Files

- `JevClient` - HTTP call to OpenRouter's decisions endpoint, retries, configuration
- `JevQuestion` / `JevAnswer` - the noul, choice and score primitives
- `JevState` - the game as the model sees it, including available mana and land drop
- `JevOptions` - legal options in, chosen game object out
- `JevPlayer` - the pure player, one method per decision
- `JevHybridPlayer` - the search wrapped at both ends
