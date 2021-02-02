
package mage.cards.t;

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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Thunderclap extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Thunderclap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // You may sacrifice a Mountain rather than pay Thunderclap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter))));

        // Thunderclap deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Thunderclap(final Thunderclap card) {
        super(card);
    }

    @Override
    public Thunderclap copy() {
        return new Thunderclap(this);
    }
}
