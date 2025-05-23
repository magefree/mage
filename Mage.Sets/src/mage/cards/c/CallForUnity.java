package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CallForUnity extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.UNITY);

    public CallForUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, put a unity counter on Call for Unity.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.UNITY.createInstance()))
                .withInterveningIf(RevoltCondition.instance)
                .setAbilityWord(AbilityWord.REVOLT)
                .addHint(RevoltCondition.getHint()), new RevoltWatcher());

        // Creatures you control get +1/+1 for each unity counter on Call for Unity.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                xValue, xValue, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE, false
        ).setText("creatures you control get +1/+1 for each unity counter on {this}")));
    }

    private CallForUnity(final CallForUnity card) {
        super(card);
    }

    @Override
    public CallForUnity copy() {
        return new CallForUnity(this);
    }
}
