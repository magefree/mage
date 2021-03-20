package mage.abilities;

import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public interface OpeningHandAction {

    boolean askUseOpeningHandAction(Card card, Player player, Game game);

    boolean isOpeningHandActionAllowed(Card card, Player player, Game game);

    void doOpeningHandAction(Card card, Player player, Game game);

}
