
package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SunDroplet extends CardImpl {

    public SunDroplet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you're dealt damage, put that many charge counters on Sun Droplet.
        this.addAbility(new SunDropletTriggeredAbility());

        // At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new GainLifeEffect(1), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance())
                ), TargetController.EACH_PLAYER, false
        ));
    }

    private SunDroplet(final SunDroplet card) {
        super(card);
    }

    @Override
    public SunDroplet copy() {
        return new SunDroplet(this);
    }
}

class SunDropletTriggeredAbility extends TriggeredAbilityImpl {

    SunDropletTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), false);
    }

    private SunDropletTriggeredAbility(final SunDropletTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunDropletTriggeredAbility copy() {
        return new SunDropletTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().clear();
            if (event.getAmount() > 0) {
                this.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(event.getAmount())));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, put that many charge counters on {this}.";
    }
}
