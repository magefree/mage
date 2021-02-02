

package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Deprive extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("land");

    public Deprive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        // As an additional cost to cast Deprive, return a land you control to its owner's hand.
        this.getSpellAbility().addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));

        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private Deprive(final Deprive card) {
        super(card);
    }

    @Override
    public Deprive copy() {
        return new Deprive(this);
    }

}
