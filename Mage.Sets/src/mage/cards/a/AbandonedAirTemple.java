package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlABasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbandonedAirTemple extends CardImpl {

    public AbandonedAirTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlABasicLandCondition.instance)
                .addHint(YouControlABasicLandCondition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {3}{W}, {T}: Put a +1/+1 counter on each creature you control.
        Ability ability = new SimpleActivatedAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), new ManaCostsImpl<>("{3}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AbandonedAirTemple(final AbandonedAirTemple card) {
        super(card);
    }

    @Override
    public AbandonedAirTemple copy() {
        return new AbandonedAirTemple(this);
    }
}
