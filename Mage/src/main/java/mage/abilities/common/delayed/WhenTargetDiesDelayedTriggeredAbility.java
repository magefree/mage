package mage.abilities.common.delayed;

import mage.MageObjectReference;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author awjackson
 */
public class WhenTargetDiesDelayedTriggeredAbility extends DelayedTriggeredAbility {

    protected MageObjectReference mor;
    private final SetTargetPointer setTargetPointer;

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(effect, Duration.EndOfTurn, setTargetPointer);
    }

    public WhenTargetDiesDelayedTriggeredAbility(Effect effect, Duration duration, SetTargetPointer setTargetPointer) {
        super(effect, duration, true);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("When that creature dies" + (duration == Duration.EndOfTurn ? " this turn, " : ", "));
    }

    public WhenTargetDiesDelayedTriggeredAbility(final WhenTargetDiesDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
        this.setTargetPointer = ability.setTargetPointer;
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (mor == null || !mor.refersTo(permanent, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case CARD:
                // card in graveyard
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case PERMANENT:
                // LKI of permanent
                getEffects().setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                break;
            case PLAYER:
                // LKI permanent's controller
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
        }
        return true;
    }
}
