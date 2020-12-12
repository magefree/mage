
package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodHound extends CardImpl {

    public BloodHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you're dealt damage, you may put that many +1/+1 counters on Blood Hound.
        this.addAbility(new BloodHoundTriggeredAbility());

        // At the beginning of your end step, remove all +1/+1 counters from Blood Hound.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new RemoveAllCountersSourceEffect(CounterType.P1P1), TargetController.YOU, false
        ));
    }

    private BloodHound(final BloodHound card) {
        super(card);
    }

    @Override
    public BloodHound copy() {
        return new BloodHound(this);
    }
}

class BloodHoundTriggeredAbility extends TriggeredAbilityImpl {

    BloodHoundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    private BloodHoundTriggeredAbility(final BloodHoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodHoundTriggeredAbility copy() {
        return new BloodHoundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId()) && event.getAmount() > 0) {
            this.getEffects().clear();
            if (event.getAmount() > 0) {
                this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(event.getAmount())));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, you may put that many +1/+1 counters on {this}.";
    }
}
