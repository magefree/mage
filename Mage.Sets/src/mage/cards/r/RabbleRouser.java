
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author BursegSardaukar
 */
public final class RabbleRouser extends CardImpl {

    public RabbleRouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Bloodthirst 1 (If an opponent was dealt damage this turn, this creature enters the battlefield with a +1/+1 counter on it.)
        this.addAbility(new BloodthirstAbility(1));

        //{R}, {T}: Attacking creatures get +X/+0 until end of turn, where X is Rabble-Rouser's power.
        DynamicValue amount = new SourcePermanentPowerCount();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostAllEffect(amount, StaticValue.get(0), Duration.EndOfTurn, new FilterAttackingCreature(), false,
                        "Attacking creatures get +X/+0 until end of turn, where X is {this}'s power", true),
                new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RabbleRouser(final RabbleRouser card) {
        super(card);
    }

    @Override
    public RabbleRouser copy() {
        return new RabbleRouser(this);
    }
}
