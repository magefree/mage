
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class PulseOfTheGrid extends CardImpl {

    public PulseOfTheGrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Draw two cards, then discard a card. Then if an opponent has more cards in hand than you, return Pulse of the Grid to its owner's hand.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new PulseOfTheGridReturnToHandEffect());
    }

    private PulseOfTheGrid(final PulseOfTheGrid card) {
        super(card);
    }

    @Override
    public PulseOfTheGrid copy() {
        return new PulseOfTheGrid(this);
    }
}

class PulseOfTheGridReturnToHandEffect extends OneShotEffect {

    PulseOfTheGridReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if an opponent has more cards in hand than you, return {this} to its owner's hand";
    }

    private PulseOfTheGridReturnToHandEffect(final PulseOfTheGridReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheGridReturnToHandEffect copy() {
        return new PulseOfTheGridReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getHand().size() > controller.getHand().size()) {
                    Card card = game.getCard(source.getSourceId());
                    controller.moveCards(card, Zone.HAND, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
