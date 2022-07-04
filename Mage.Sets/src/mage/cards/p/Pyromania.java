
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
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
public final class Pyromania extends CardImpl {

    public Pyromania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // {1}{R}, Discard a card at random: Pyromania deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new DiscardCardCost(true));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // {1}{R}, Sacrifice Pyromania: Pyromania deals 1 damage to any target.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Pyromania(final Pyromania card) {
        super(card);
    }

    @Override
    public Pyromania copy() {
        return new Pyromania(this);
    }
}
