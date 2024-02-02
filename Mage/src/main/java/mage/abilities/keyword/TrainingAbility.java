package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class TrainingAbility extends TriggeredAbilityImpl {

    public TrainingAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        addWatcher(new TrainingWatcher());
    }

    private TrainingAbility(final TrainingAbility ability) {
        super(ability);
    }

    @Override
    public TrainingAbility copy() {
        return new TrainingAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackers().contains(this.getSourceId())) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .anyMatch(x -> x > permanent.getPower().getValue());
    }

    @Override
    public String getRule() {
        return "Training <i>(Whenever this creature attacks with another creature " +
                "with greater power, put a +1/+1 counter on this creature.)</i>";
    }
}

class TrainingWatcher extends Watcher {

    public TrainingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // 20240202 - 702.149c
        // Some creatures with training have abilities that trigger when they train.
        // "When this creature trains" means "When a resolving training ability puts a +1/+1 counter on this creature."
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED && event.getData().equals(CounterType.P1P1.getName())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (!(stackObject instanceof StackAbility)) {
                return;
            }

            Ability ability = stackObject.getStackAbility();
            if (ability instanceof TrainingAbility) {
                game.fireEvent(GameEvent.getEvent(
                        GameEvent.EventType.TRAINED_CREATURE,
                        event.getTargetId(),
                        ability,
                        event.getPlayerId()
                ));
            }
        }
    }
}
