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

## C. Density backlog — code-level oversized controls (LAF can't reach these)

The shared FlatLaf density tweaks tighten *standard* Swing controls, but these are sized in Java
(fixed pixels / `GUISizeHelper`) and need per-area shell work to modernize:

- ✅ **In-game command buttons are double-height.** `Dimension(2 * gameCommandButtonHeight,
  gameCommandButtonHeight)` and similar (`GamePanel.java:565`). *Addressed:* `ShellDensity` trims
  `gameCommandButtonHeight` (×0.80) centrally via the `GUISizeHelper` seam.
- ✅ **Phase buttons are fixed square icons.** `phaseButton.setPreferredSize(new Dimension(buttonSize,
  buttonSize))` driven by `GUISizeHelper` (`GamePanel.java:587-590`). *Addressed:* `ShellDensity`
  trims `gamePhaseButtonSize` (×0.80).
- ✅ **Player panel is fixed and chunky.** `PANEL_WIDTH = 94`, `PANEL_HEIGHT = 270`
  (`PlayerPanelExt.java:62-63`) — also the play-area tax noted in section A. *Addressed:*
  `ShellDensity` trims `playerPanelGuiScale` (×0.88), which scales the whole panel down.
- 🟡 **Hard-coded control borders bypass the theme.** Several status fields use
  `new LineBorder(new Color(153,153,153), 1, true)` (`GamePanel.java:2389+`) instead of theme
  colors; restyle when touching those panels.

*Lever:* tackle these in a Phase 2/3 "density" sweep, area by area, each behind `Shell.isEnabled()`
so stock sizing is preserved when the shell is off.

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
