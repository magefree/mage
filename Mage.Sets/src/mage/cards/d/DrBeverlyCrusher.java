package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DrBeverlyCrusher extends CardImpl {

    public DrBeverlyCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If you gaining life causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new DrBeverlyCrusherEffect()));

        // Whenever you gain life, put a +1/+1 counter on Dr. Crusher.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private DrBeverlyCrusher(final DrBeverlyCrusher card) {
        super(card);
    }

    @Override
    public DrBeverlyCrusher copy() {
        return new DrBeverlyCrusher(this);
    }
}
class DrBeverlyCrusherEffect extends ReplacementEffectImpl {

    DrBeverlyCrusherEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you gaining life causes a triggered ability of a permanent you control to trigger, " +
                "that ability triggers an additional time.";
    }

    private DrBeverlyCrusherEffect(final DrBeverlyCrusherEffect effect) {
        super(effect);
    }

    @Override
    public DrBeverlyCrusherEffect copy() {
        return new DrBeverlyCrusherEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            if (source.isControlledBy(event.getPlayerId())
                    && game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId()) != null
                    && numberOfTriggersEvent.getSourceEvent().getType() == GameEvent.EventType.GAINED_LIFE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
