
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnSourceFromGraveyardToHandEffect extends OneShotEffect {

    public ReturnSourceFromGraveyardToHandEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return {this} from your graveyard to your hand";
    }

    public ReturnSourceFromGraveyardToHandEffect(final ReturnSourceFromGraveyardToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSourceFromGraveyardToHandEffect copy() {
        return new ReturnSourceFromGraveyardToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = controller.getGraveyard().get(source.getSourceId(), game);
        if (card != null) {
            return controller.moveCards(card, Zone.HAND, source, game);
        }
        return false;
    }

}
