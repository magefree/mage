package mage.actions.score;

import mage.cards.Card;
import mage.game.Game;

/**
 * @author ayratn
 */
public interface ScoringSystem {

    int getLoseGameScore(final Game game);
    int getCardScore(final Card card);
}
