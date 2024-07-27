package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

public class ActivateAbilityTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;
    protected final SetTargetPointer setTargetPointer;

    public ActivateAbilityTriggeredAbility(Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer);
    }

    public ActivateAbilityTriggeredAbility(Zone zone, Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer) {
        super(zone, effect, false);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever you activate " + filter.getMessage() + ", ");
    }

    private ActivateAbilityTriggeredAbility(final ActivateAbilityTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public ActivateAbilityTriggeredAbility copy() {
        return new ActivateAbilityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
        if (stackAbility == null) {
            return false;
        }

        if (!filter.match(stackAbility, event.getPlayerId(), this, game)) {
            return false;
        }

        switch (setTargetPointer) {
            case NONE:
                break;
            case PLAYER:
                getAllEffects().setTargetPointer(new FixedTarget(getControllerId(), game));
                break;
            case SPELL:
                getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            default:
                throw new UnsupportedOperationException("Unexpected setTargetPointer in ActivateAbilityTriggeredAbility: " + setTargetPointer);
        }
        return true;
    }
}
