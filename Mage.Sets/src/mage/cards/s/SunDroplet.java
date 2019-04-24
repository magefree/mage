
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class SunDroplet extends CardImpl {

    public SunDroplet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever you're dealt damage, put that many charge counters on Sun Droplet.
        this.addAbility(new SunDropletTriggeredAbility());
        // At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life.
        //TODO this shouldn't be conditional because you can respond to the trigger by adding counters.
        Effect effect = new DoIfCostPaid(new GainLifeEffect(1), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.ANY, false),
                new SourceHasCounterCondition(CounterType.CHARGE, 1),
                "At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life"));
    }

    public SunDroplet(final SunDroplet card) {
        super(card);
    }

    @Override
    public SunDroplet copy() {
        return new SunDroplet(this);
    }
}

class SunDropletTriggeredAbility extends TriggeredAbilityImpl {

    public SunDropletTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SunDropletEffect(), false);
    }

    public SunDropletTriggeredAbility(final SunDropletTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunDropletTriggeredAbility copy() {
        return new SunDropletTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, put that many charge counters on {this}.";
    }
}

class SunDropletEffect extends OneShotEffect {

    public SunDropletEffect() {
        super(Outcome.Benefit);
    }

    public SunDropletEffect(final SunDropletEffect effect) {
        super(effect);
    }

    @Override
    public SunDropletEffect copy() {
        return new SunDropletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.CHARGE.createInstance((Integer) this.getValue("damageAmount"))).apply(game, source);
    }
}
