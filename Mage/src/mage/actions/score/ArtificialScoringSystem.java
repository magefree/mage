package mage.actions.score;

import mage.cards.Card;
import mage.game.Game;
import org.apache.log4j.Logger;

/**
 * @author ayratn
 */
public class ArtificialScoringSystem implements ScoringSystem {

    public static ArtificialScoringSystem inst;

    private static final transient Logger log = Logger.getLogger(ArtificialScoringSystem.class);

    static {
        inst = new ArtificialScoringSystem();
        log.info("ArtificialScoringSystem has been instantiated.");
    }

    /**
     * Lose score is lowered in function of the turn and phase when it occurs.
     * Encourages AI to win as fast as possible.
     *
     * @param game
     * @return
     */
    public int getLoseGameScore(final Game game) {
        if (game.getStep() == null) {
            return 0;
        }
        return ScoringConstants.LOSE_GAME_SCORE + game.getTurnNum() * 2500 + game.getStep().getType().getIndex() * 200;
    }

    @Override
    public int getCardScore(Card card) {
        //TODO: implement
        return ScoringConstants.UNKNOWN_CARD_SCORE;
    }

}