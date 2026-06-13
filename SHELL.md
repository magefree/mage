# Modern UI Shell

An **opt-in, additive** modernization layer for the XMage Swing client. It is designed to
**survive upstream `master` updates that ignore our changes**: the overwhelming majority of the
code lives in brand-new files (which never produce merge conflicts), and it touches upstream code
at a tiny, documented set of "seams."

## Status

- **Phase 0 — Foundation** ✅ feature flag, FlatLaf installer, seam, this manifest.
- **Phase 1 — Theming** ✅ dark ("Arcane Dark") + light ("Arcane Parchment") variants, magic-flavored
  palette, rounded controls, slim scrollbars — all via additive `FlatLaf.properties` files.
- **Phase 2 — Component restyling & density** 🚧 in progress:
  - Collapsible in-game chat — **starts closed**, reclaims play-area width, and **pulses** an unread
    badge on new player messages.
  - Global density pass for standard Swing controls (tighter button/menu/combo/list padding) — the
    start of modernizing XMage's old low-res-era sizing.
  - In-game play-control density (`ShellDensity`): trims phase icons, skip/command buttons, and the
    player panel centrally via `GUISizeHelper`. Remaining code-level items tracked in
    `SHELL_OBSERVATIONS.md` (section C).
- **Phase 3 — Structural / interaction** ⏳ planned (see `SHELL_OBSERVATIONS.md` for play-area leads).

See `SHELL_OBSERVATIONS.md` for a passive catalog of memory and play-area/4-player observations
collected while building the shell.

## How to enable

The shell is **off by default** — stock Nimbus is untouched. Turn it on with either:

```bash
# environment variable
XMAGE_SHELL=1   java ... mage.client.MageFrame

# or system property
java -Dxmage.shell=1 ... mage.client.MageFrame
```

Accepted truthy values: `1`, `true`, `on`, `yes`, `enabled`.

Pick the variant (defaults to dark) with `XMAGE_SHELL_THEME` / `-Dxmage.shell.theme`:

```bash
XMAGE_SHELL=1 XMAGE_SHELL_THEME=light   java ...   # Arcane Parchment (light)
XMAGE_SHELL=1                           java ...   # Arcane Dark (default)
```

## Architecture & survivability strategy

1. **Additive-first.** Nearly all shell code is new files under
   `Mage.Client/src/main/java/mage/client/shell/` and additive resources under
   `Mage.Client/src/main/resources/mage/client/shell/`. New files merge cleanly forever.
2. **Minimal seams.** Where upstream code *must* call into the shell, the edit is a single guarded
   line behind `Shell.isEnabled()`. Each seam is tagged with the marker `[modern-shell]` so they
   are greppable, and listed in the table below.
3. **Opt-in / default off.** With the flag unset, the seams are inert and the client behaves exactly
   like upstream. This makes A/B comparison trivial and keeps risk near zero.
4. **Theming as data.** Visual styling lives in `FlatLaf.properties` (a FlatLaf custom defaults
   source), so most look changes need no Java edits at all.

## Seam registry

Every upstream-file edit lives here. After a hard merge from `master`, `grep -rn "\[modern-shell\]"`
to find any seam that didn't apply, and re-insert it from this table.

| # | File | What | Why it's conflict-resistant |
|---|------|------|------------------------------|
| 1 | `Mage.Client/pom.xml` | Adds the `com.formdev:flatlaf` dependency at the top of `<dependencies>`. | Pure addition; dependency lists rarely conflict on the same line. |
| 2 | `Mage.Client/src/main/java/mage/client/util/gui/GuiDisplayUtil.java` | In `refreshThemeSettings()`, wraps the single `UIManager.setLookAndFeel("...Nimbus...")` call in `if (Shell.isEnabled()) { Shell.installLookAndFeel(); } else { ...existing Nimbus... }`. | One spot, inside the existing `try`/`catch`; the `else` keeps the original call verbatim. |
| 3 | `Mage.Client/src/main/java/mage/client/game/GamePanel.java` | After `splitBattlefieldAndChats` is assembled, `if (Shell.isEnabled()) { ShellChat.install(splitBattlefieldAndChats, userChatPanel); }`. | One guarded line right after the split is built; `ShellChat` wraps the chat side, touching no upstream chat code. |
| 4 | `Mage.Client/src/main/java/mage/client/util/GUISizeHelper.java` | At the end of `calculateGUISizes()`, `if (Shell.isEnabled()) { ShellDensity.applyInGameControls(); }`. | One guarded line at the method's end; only scales already-public size fields, idempotent across recomputes. |

> Keep the seam count low. Prefer subclass-and-swap, `UIManager` overrides, and FlatLaf
> `.properties` over editing more upstream files.

## Layout

```
SHELL.md                                              <- this file
Mage.Client/
  pom.xml                                             <- seam #1 (FlatLaf dependency)
  src/main/java/mage/client/
    shell/Shell.java                                  <- flag + LAF installer (new)
    shell/ShellChat.java                              <- collapsible chat + unread badge (new)
    shell/ShellDensity.java                           <- in-game control density trim (new)
    util/gui/GuiDisplayUtil.java                      <- seam #2 (LAF install)
    util/GUISizeHelper.java                           <- seam #4 (in-game density)
    game/GamePanel.java                               <- seam #3 (collapsible chat install)
  src/main/resources/mage/client/shell/
    FlatLaf.properties                                <- shared theme tuning (new)
    FlatDarkLaf.properties                            <- dark variant palette (new)
    FlatLightLaf.properties                           <- light variant palette (new)
SHELL_OBSERVATIONS.md                                 <- passive memory/play-area catalog (new)
```

## Roadmap

- **Phase 1 (theming, central only):** finish dark/light variants, magic-flavored accents, fonts,
  spacing — all via `UIManager` keys / `.properties`, zero per-component edits.
- **Phase 2 (component restyling):** targeted FlatLaf `styleClass`/`ComponentUI` overrides for the
  table list, chat panel, and toolbar; modern iconography as additive resources.
- **Phase 3 (structural):** alternate layouts for specific panels via subclassing and swapping at
  construction seams.
