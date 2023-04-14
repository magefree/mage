
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class Fireblast extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Fireblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}{R}");

        // You may sacrifice two Mountains rather than pay Fireblast's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));

        // Fireblast deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    private Fireblast(final Fireblast card) {
        super(card);
    }

    @Override
    public Fireblast copy() {
        return new Fireblast(this);
    }
}
