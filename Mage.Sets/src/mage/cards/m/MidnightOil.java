package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MidnightOil extends CardImpl {

    public MidnightOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Midnight Oil enters the battlefield with seven hour counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.HOUR.createInstance(7)
                ), "with seven hour counters on it"
        ));

        // At the beginning of your draw step, draw an additional card and remove two hour counters from Midnight Oil.
        Ability ability = new BeginningOfDrawTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("draw an additional card"),
                TargetController.YOU, false
        );
        ability.addEffect(new RemoveCounterSourceEffect(
                CounterType.HOUR.createInstance(2)
        ).concatBy("and"));
        this.addAbility(ability);

        // Your maximum hand size is equal to the number of hour counters on Midnight Oil.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                new CountersSourceCount(CounterType.HOUR), Duration.WhileOnBattlefield,
                HandSizeModification.SET, TargetController.YOU
        ).setText("your maximum hand size is equal to the number of hour counters on {this}")));

        // Whenever you discard a card, you lose 1 life.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), false
        ));
    }

    private MidnightOil(final MidnightOil card) {
        super(card);
    }

    @Override
    public MidnightOil copy() {
        return new MidnightOil(this);
    }
}
