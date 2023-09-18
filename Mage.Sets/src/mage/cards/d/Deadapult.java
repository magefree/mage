
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class Deadapult extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public Deadapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // {R}, Sacrifice a Zombie: Deadapult deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Deadapult(final Deadapult card) {
        super(card);
    }

    @Override
    public Deadapult copy() {
        return new Deadapult(this);
    }
}
