
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
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
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class MidnightOil extends CardImpl {

    public MidnightOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // Midnight Oil enters the battlefield with seven hour counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(new Counter(CounterType.HOUR.createInstance(7))),
                "with seven hour counters on it"));

        // At the beginning of your draw step, draw an additional card and remove two hour counters from Midnight Oil.
        Ability ability = new BeginningOfDrawTriggeredAbility(new DrawCardSourceControllerEffect(1), TargetController.YOU, false);
        Effect effect = new RemoveCounterSourceEffect(CounterType.HOUR.createInstance(2));
        effect.setText("and remove two hour counters from {this}");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Your maximum hand size is equal to the number of hour counters on Midnight Oil.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaximumHandSizeControllerEffect(new CountersSourceCount(CounterType.HOUR), Duration.WhileOnBattlefield, HandSizeModification.SET, TargetController.YOU)));

        // Whenever you discard a card, you lose 1 life.
        this.addAbility(new MidnightOilTriggeredAbility(new LoseLifeSourceControllerEffect(1)));

    }

    public MidnightOil(final MidnightOil card) {
        super(card);
    }

    @Override
    public MidnightOil copy() {
        return new MidnightOil(this);
    }
}

class MidnightOilTriggeredAbility extends TriggeredAbilityImpl {

    MidnightOilTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    MidnightOilTriggeredAbility(final MidnightOilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MidnightOilTriggeredAbility copy() {
        return new MidnightOilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());

    }

    @Override
    public String getRule() {
        return "Whenever you discard a card, " + super.getRule();
    }
}
