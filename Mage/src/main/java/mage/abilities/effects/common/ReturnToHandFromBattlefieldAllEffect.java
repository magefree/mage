
package mage.abilities.effects.common;

import java.util.HashSet;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class ReturnToHandFromBattlefieldAllEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public ReturnToHandFromBattlefieldAllEffect(FilterPermanent filter) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        staticText = "return all " + filter.getMessage() + " to their owners' hands";
    }

    public ReturnToHandFromBattlefieldAllEffect(final ReturnToHandFromBattlefieldAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> permanentsToHand = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanentsToHand.add(permanent);
            }
            controller.moveCards(permanentsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;

    }

    @Override
    public ReturnToHandFromBattlefieldAllEffect copy() {
        return new ReturnToHandFromBattlefieldAllEffect(this);
    }
}
