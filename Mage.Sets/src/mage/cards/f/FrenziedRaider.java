package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrenziedRaider extends CardImpl {

    public FrenziedRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you activate a boast ability, put a +1/+1 counter on Frenzied Raider.
        this.addAbility(new FrenziedRaiderTriggeredAbility());
    }

    private FrenziedRaider(final FrenziedRaider card) {
        super(card);
    }

    @Override
    public FrenziedRaider copy() {
        return new FrenziedRaider(this);
    }
}

class FrenziedRaiderTriggeredAbility extends TriggeredAbilityImpl {

    FrenziedRaiderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private FrenziedRaiderTriggeredAbility(final FrenziedRaiderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrenziedRaiderTriggeredAbility copy() {
        return new FrenziedRaiderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
        return stackAbility != null && stackAbility.getStackAbility() instanceof BoastAbility;
    }

    @Override
    public String getRule() {
        return "Whenever you activate a boast ability, put a +1/+1 counter on {this}.";
    }
}
