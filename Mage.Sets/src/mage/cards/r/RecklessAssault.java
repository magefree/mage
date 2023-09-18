
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class RecklessAssault extends CardImpl {

    public RecklessAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{R}");

        // {1}, Pay 2 life: Reckless Assault deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new PayLifeCost(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RecklessAssault(final RecklessAssault card) {
        super(card);
    }

    @Override
    public RecklessAssault copy() {
        return new RecklessAssault(this);
    }
}
