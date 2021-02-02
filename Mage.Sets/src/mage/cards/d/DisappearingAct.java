
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class DisappearingAct extends CardImpl {

    public DisappearingAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // As an additional cost to cast Disappearing Act, return a permanent you control to its owner's hand.
        this.getSpellAbility().addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(new FilterControlledPermanent("a permanent"))));

        // Counter target spell.
        getSpellAbility().addEffect(new CounterTargetEffect());
        getSpellAbility().addTarget(new TargetSpell());
    }

    private DisappearingAct(final DisappearingAct card) {
        super(card);
    }

    @Override
    public DisappearingAct copy() {
        return new DisappearingAct(this);
    }
}
