package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author BursegSardaukar
 */
public final class RabbleRouser extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public RabbleRouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Bloodthirst 1 (If an opponent was dealt damage this turn, this creature enters the battlefield with a +1/+1 counter on it.)
        this.addAbility(new BloodthirstAbility(1));

        //{R}, {T}: Attacking creatures get +X/+0 until end of turn, where X is Rabble-Rouser's power.

        Ability ability = new SimpleActivatedAbility(
                new BoostAllEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false, null, true),
                new ColoredManaCost(ColoredManaSymbol.R));
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
