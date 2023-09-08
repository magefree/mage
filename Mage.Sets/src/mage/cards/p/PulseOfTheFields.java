
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class PulseOfTheFields extends CardImpl {

    public PulseOfTheFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // You gain 4 life. Then if an opponent has more life than you, return Pulse of the Fields to its owner's hand.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
        this.getSpellAbility().addEffect(new PulseOfTheFieldsReturnToHandEffect());
    }

    private PulseOfTheFields(final PulseOfTheFields card) {
        super(card);
    }

    @Override
    public PulseOfTheFields copy() {
        return new PulseOfTheFields(this);
    }
}

class PulseOfTheFieldsReturnToHandEffect extends OneShotEffect {

    PulseOfTheFieldsReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if an opponent has more life than you, return {this} to its owner's hand";
    }

    private PulseOfTheFieldsReturnToHandEffect(final PulseOfTheFieldsReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheFieldsReturnToHandEffect copy() {
        return new PulseOfTheFieldsReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getLife() > controller.getLife()) {
                    Card card = game.getCard(source.getSourceId());
                    controller.moveCards(card, Zone.HAND, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
