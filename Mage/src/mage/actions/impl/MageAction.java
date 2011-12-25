package mage.actions.impl;

import mage.game.Game;
import mage.players.Player;

/**
 * Base class for mage actions.
 *
 * @author ayratn
 */
public abstract class MageAction {

    /**
     * {@link Player} we count score for.
     */
    private Player scorePlayer;

    /**
     * Current game score for the player.
     */
    private int score = 0;

    /**
     * Set or change action score.
     *
     * @param scorePlayer Set player.
     * @param score       Set score value.
     */
    protected void setScore(Player scorePlayer, int score) {
        this.scorePlayer = scorePlayer;
        this.score = score;
    }

    /**
     * Get game score for the {@link Player}.
     * Value depends on the owner of this action.
     * In case player and owner differ, negative value is returned.
     *
     * @param player
     * @return
     */
    public int getScore(final Player player) {
        if (player.getId().equals(scorePlayer.getId())) {
            return score;
        } else {
            return -score;
        }
    }

    /**
     * Execute action.
     *
     * @param game Game context.
     */
    public abstract int doAction(final Game game);

    /**
     * Undo action.
     *
     * @param game Game context
     */
    public abstract void undoAction(final Game game);

    @Override
    public String toString() {
        return "";
    }
}
