
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
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
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class BlazingHellhound extends CardImpl {

    public BlazingHellhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.ELEMENTAL, SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature: Blazing Hellhound deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BlazingHellhound(final BlazingHellhound card) {
        super(card);
    }

    @Override
    public BlazingHellhound copy() {
        return new BlazingHellhound(this);
    }
}
