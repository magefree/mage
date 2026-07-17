package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author muz
 */
public final class ComfortingCounsel extends CardImpl {

    public ComfortingCounsel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever you gain life, put a growth counter on this enchantment.
        this.addAbility(new GainLifeControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.GROWTH.createInstance())
        ));

        // As long as there are five or more growth counters on this enchantment, creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield),
            new SourceHasCounterCondition(CounterType.GROWTH, 5),
            "as long as there are five or more growth counters on {this}, creatures you control get +3/+3"
        )));
    }

    private ComfortingCounsel(final ComfortingCounsel card) {
        super(card);
    }

    @Override
    public ComfortingCounsel copy() {
        return new ComfortingCounsel(this);
    }
}
