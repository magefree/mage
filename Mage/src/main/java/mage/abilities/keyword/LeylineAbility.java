
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.OpeningHandAction;
import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LeylineAbility extends StaticAbility implements MageSingleton, OpeningHandAction {

    private static final LeylineAbility instance = new LeylineAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static LeylineAbility getInstance() {
        return instance;
    }

    private LeylineAbility() {
        super(Zone.HAND, null);
    }

    @Override
    public String getRule() {
        return "If {this} is in your opening hand, you may begin the game with it on the battlefield.";
    }

    @Override
    public LeylineAbility copy() {
        return instance;
    }

    @Override
    public boolean askUseOpeningHandAction(Card card, Player player, Game game) {
        return player.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " on the battlefield?", this, game);
    }

    @Override
    public boolean isOpeningHandActionAllowed(Card card, Player player, Game game) {
        return true;
    }

    @Override
    public void doOpeningHandAction(Card card, Player player, Game game) {
        player.moveCards(card, Zone.BATTLEFIELD, this, game);
    }
}
