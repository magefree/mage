package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GuardianShieldBearer extends CardImpl {

    public GuardianShieldBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Megamorph {3}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{3}{G}"), true));

        // When Guardian Shield-Bearer is turned face up, put a +1/+1 counter on another target creature you control.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private GuardianShieldBearer(final GuardianShieldBearer card) {
        super(card);
    }

    @Override
    public GuardianShieldBearer copy() {
        return new GuardianShieldBearer(this);
    }
}
