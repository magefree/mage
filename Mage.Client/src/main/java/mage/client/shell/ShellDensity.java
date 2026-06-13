package mage.client.shell;

import mage.client.util.GUISizeHelper;

/**
 * Phase 2 density: trims XMage's old, low-resolution-era in-game control sizes so the play view
 * reads as a modern, compact application.
 * <p>
 * Survivability: the central sizing authority {@code GUISizeHelper.calculateGUISizes()} calls
 * {@link #applyInGameControls()} from a single guarded seam (see {@code SHELL.md}). Because that
 * method recomputes the base sizes from user preferences on every call, applying our multipliers
 * afterwards is <b>idempotent</b> (never compounds) and stays proportional to the user's chosen
 * sizes — we just scale everything down a notch.
 * <p>
 * All multipliers live here as named constants so density can be tuned without touching upstream.
 *
 * @author modern-shell
 */
public final class ShellDensity {

    /** Phase icon buttons (base 36px) — slightly smaller, still legible. */
    private static final float PHASE_BUTTON_FACTOR = 0.80f;
    /** Skip/command buttons (base 32px height). */
    private static final float COMMAND_BUTTON_FACTOR = 0.80f;
    /** Player info panel (avatar + mana). Also reclaims play-area width, esp. in 4-player. */
    private static final float PLAYER_PANEL_FACTOR = 0.88f;

    // Floors so density never makes controls unusably small regardless of the user's size pref.
    private static final int MIN_PHASE_BUTTON = 22;
    private static final int MIN_COMMAND_BUTTON = 20;

    private ShellDensity() {
    }

    /**
     * Trim the in-game play controls (phase icons, skip/command buttons, player panel).
     * Safe to call repeatedly — {@code calculateGUISizes()} resets the base values first.
     */
    public static void applyInGameControls() {
        GUISizeHelper.gamePhaseButtonSize = Math.max(MIN_PHASE_BUTTON,
                Math.round(GUISizeHelper.gamePhaseButtonSize * PHASE_BUTTON_FACTOR));
        GUISizeHelper.gameCommandButtonHeight = Math.max(MIN_COMMAND_BUTTON,
                Math.round(GUISizeHelper.gameCommandButtonHeight * COMMAND_BUTTON_FACTOR));
        GUISizeHelper.playerPanelGuiScale *= PLAYER_PANEL_FACTOR;
    }
}
