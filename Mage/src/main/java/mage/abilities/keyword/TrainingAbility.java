package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class TrainingAbility extends TriggeredAbilityImpl {

    public TrainingAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
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
