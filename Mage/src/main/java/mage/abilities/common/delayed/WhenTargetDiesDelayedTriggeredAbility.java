package mage.abilities.common.delayed;

import mage.MageObjectReference;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author awjackson
 */
public class WhenTargetDiesDelayedTriggeredAbility extends WhenTargetLeavesBattlefieldDelayedTriggeredAbility {

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(effect, Duration.EndOfTurn, setTargetPointer);
    }

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect, Duration duration, SetTargetPointer setTargetPointer) {
        super(effect, duration, setTargetPointer);
        setTriggerPhrase("When that creature dies" + (duration == Duration.EndOfTurn ? " this turn, " : ", "));
    }

    protected WhenTargetDiesDelayedTriggeredAbility(final WhenTargetDiesDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WhenTargetDiesDelayedTriggeredAbility copy() {
        return new WhenTargetDiesDelayedTriggeredAbility(this);
    }

    @Override
    public void init(Game game) {
        mor = new MageObjectReference(getFirstTarget(), game);
        getTargets().clear();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeEvent) event).isDiesEvent() && super.checkTrigger(event, game);
    }
}
