
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class RiversRebuke extends CardImpl {

    public RiversRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all nonland permanents target player controls to their owner's hand.
        this.getSpellAbility().addEffect(new RiversRebukeReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public RiversRebuke(final RiversRebuke card) {
        super(card);
    }

    @Override
    public RiversRebuke copy() {
        return new RiversRebuke(this);
    }
}

class RiversRebukeReturnToHandEffect extends OneShotEffect {

    public RiversRebukeReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all nonland permanents target player controls to their owner's hand";
    }

    public RiversRebukeReturnToHandEffect(final RiversRebukeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (targetPointer.getFirst(game, source) != null) {
            FilterNonlandPermanent filter = new FilterNonlandPermanent();
            filter.add(new ControllerIdPredicate(targetPointer.getFirst(game, source)));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public RiversRebukeReturnToHandEffect copy() {
        return new RiversRebukeReturnToHandEffect(this);
    }
}
