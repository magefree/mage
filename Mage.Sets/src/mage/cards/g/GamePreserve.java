
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class GamePreserve extends CardImpl {

    public GamePreserve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, each player reveals the top card of their library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DuskmarEffect(), TargetController.YOU, false));
    }

    public GamePreserve(final GamePreserve card) {
        super(card);
    }

    @Override
    public GamePreserve copy() {
        return new GamePreserve(this);
    }
}

class DuskmarEffect extends OneShotEffect {

    public DuskmarEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of their library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control";
    }

    public DuskmarEffect(final DuskmarEffect effect) {
        super(effect);
    }

    @Override
    public DuskmarEffect copy() {
        return new DuskmarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean putToPlay = true;
            Cards cards = new CardsImpl();
            for (Player player : game.getPlayers().values()) {
                if (player.getLibrary().hasCards()) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null) {
                        cards.add(card);
                        if (!card.isCreature()) {
                            putToPlay = false;
                        }
                        player.revealCards(source, "- Revealed by " + player.getName(), cards, game);
                    }
                } else {
                    putToPlay = false;
                }
            }
            if (putToPlay) {
                controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
