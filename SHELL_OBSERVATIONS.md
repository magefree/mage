# Modern Shell — Observations Catalog

A running, **passive** log of opportunities noticed while doing other shell work. Nothing here is a
commitment; items are leads to weigh later. Each entry notes whether it was **directly observed** in
code or is a **candidate** still needing verification, plus `file:line` anchors.

Legend: 🟢 directly observed · 🟡 candidate / needs verification

---

## A. Play-area space & opponent/own-board clarity (priority: 4-player)

- 🟢 **Per-seat fixed-width info panel tax.** Every `PlayAreaPanel` puts a `PlayerPanelExt` in
  `BorderLayout.WEST` with a fixed `PANEL_WIDTH = 94` (scaled) and `PANEL_HEIGHT = 270`.
  `PlayerPanelExt.java:62-63`, `PlayAreaPanel.java:537`. In 4-player layout this column is paid
  **4×**, eating horizontal battlefield width. *Lever:* a compact/horizontal info strip (or
  collapsed avatar) for non-active opponents could reclaim significant battlefield width.

- 🟢 **All seats weighted equally.** `addPlayers()` lays seats out in a GridBag with
  `weightx = weighty = 0.5` for everyone (`GamePanel.java:987-988, 1032-1033`), so your own board
  gets the same area as each opponent. *Lever:* weight the active player / your own seat larger,
  especially in 4-player, to improve own-board clarity.

- ✅ **Chat split consumes horizontal play area.** The main split is
  `[battlefield + hand + stack] <|> [chats]` (`GamePanel.java:2369`). *Addressed in Phase 2:*
  `ShellChat` collapses the chat to a 24px strip (reclaiming play-area width) with an unread badge.

- 🟡 **Small-mode avatar still tall.** `PANEL_HEIGHT_SMALL = 218` with a "TODO: no need in small
  mode after GUI scale added" (`PlayerPanelExt.java:64`). Opponent panels may be shrinkable further
  in multiplayer; needs verifying against current scaling.

## B. Memory / performance

- 🟡 **Card image cache.** `org/mage/plugins/card/images/ImageCache.java` and
  `DownloadPicturesService.java` are the obvious large-image memory consumers. Not yet inspected —
  flagged as the first place to profile when memory work begins.

- 🟡 **Theme refresh walks every frame + shared component.** `GuiDisplayUtil.refreshThemeSettings()`
  iterates all `Frame`s and all `MageComponents` calling `updateComponentTreeUI`
  (`GuiDisplayUtil.java:514-528`). Fine at startup, but worth confirming it isn't invoked on hot
  paths. Not a memory issue; minor CPU.

---

*Add new items at the top of the relevant section as they're noticed.*
