package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceFirstTimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author AustinYQM, xenohedron
 */
public final class AngelicCub extends CardImpl {

    public AngelicCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Angelic Cub becomes the target of a spell or ability for the first time each turn, put a +1/+1 counter on it.
        this.addAbility(new BecomesTargetSourceFirstTimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.NONE, false
        ));
        // As long as Angelic Cub has three or more +1/+1 counters on it, it has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), new SourceHasCounterCondition(CounterType.P1P1, 3), "As long as {this} has three or more +1/+1 counters on it, it has flying.")));
    }

    private AngelicCub(final AngelicCub card) {
        super(card);
    }

    @Override
    public AngelicCub copy() {
        return new AngelicCub(this);
    }
}
