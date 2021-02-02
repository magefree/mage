
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author fireshoes
 */
public final class CallForUnity extends CardImpl {

    private static final String ruleText = "<i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, "
            + "put a unity counter on {this}.";

    public CallForUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, put a unity counter on Call for Unity.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.UNITY.createInstance(), true), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, RevoltCondition.instance, ruleText), new RevoltWatcher());

        // Creatures you control get +1/+1 for each unity counter on Call for Unity.
        Effect effect = new BoostControlledEffect(new CountersSourceCount(CounterType.UNITY), new CountersSourceCount(CounterType.UNITY), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE, false);
        effect.setText("Creatures you control get +1/+1 for each unity counter on {this}");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private CallForUnity(final CallForUnity card) {
        super(card);
    }

    @Override
    public CallForUnity copy() {
        return new CallForUnity(this);
    }
}
