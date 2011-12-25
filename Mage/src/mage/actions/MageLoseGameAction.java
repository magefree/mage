package mage.actions;

import mage.actions.impl.MageAction;
import mage.actions.score.ArtificialScoringSystem;
import mage.game.Game;
import mage.players.Player;

/**
 * Lose game action.
 *
 * @author ayratn
 */
public class MageLoseGameAction extends MageAction {

    public static final String LIFE_REASON = "{L} You lose the game.";
    public static final String POISON_REASON = "{L} You are poisoned. You lose the game.";
    public static final String DRAW_REASON = "{L} You attempt to draw from an empty library. You lose the game.";

    private final Player player;
    private final String reason;
    private Player oldLosingPlayer;

    public MageLoseGameAction(final Player player, final String reason) {
        this.player = player;
        this.reason = reason;
    }

    @Override
    public int doAction(final Game game) {
        oldLosingPlayer = game.getLosingPlayer();
        if (oldLosingPlayer == null && player.canLose(game)) {
            setScore(player, ArtificialScoringSystem.inst.getLoseGameScore(game));
            game.setLosingPlayer(player);
            game.informPlayer(player, reason);
        }
        return 0;
    }

    @Override
    public void undoAction(final Game game) {
        game.setLosingPlayer(oldLosingPlayer);
    }
}