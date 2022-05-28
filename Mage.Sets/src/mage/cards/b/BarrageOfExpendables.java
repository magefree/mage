
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 *
 * @author jeffwadsworth
 */
public final class BarrageOfExpendables extends CardImpl {

    public BarrageOfExpendables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // {R}, Sacrifice a creature: Barrage of Expendables deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private BarrageOfExpendables(final BarrageOfExpendables card) {
        super(card);
    }

    @Override
    public BarrageOfExpendables copy() {
        return new BarrageOfExpendables(this);
    }
}
