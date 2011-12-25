package mage.actions.score;

import mage.cards.Card;
import mage.game.Game;

/**
 * @author ayratn
 */
public interface ScoringSystem {
    public int getLoseGameScore(final Game game);
    public int getCardScore(final Card card);
}
