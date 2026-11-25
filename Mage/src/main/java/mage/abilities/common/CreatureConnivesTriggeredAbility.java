package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * "Whenever a creature you control connives, [effect]."
 *
 * @author JayDi85
 */
public class CreatureConnivesTriggeredAbility extends TriggeredAbilityImpl {

    private final SetTargetPointer setTargetPointer;

    public CreatureConnivesTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    /**
     * @param setTargetPointer PERMANENT - the effects refer to the conniving creature ("that creature")
     */
    public CreatureConnivesTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, false);
        switch (setTargetPointer) {
            case NONE:
            case PERMANENT:
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage, setTargetPointer is not supported: " + setTargetPointer);
        }
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever a creature you control connives, ");
    }

    protected CreatureConnivesTriggeredAbility(final CreatureConnivesTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public CreatureConnivesTriggeredAbility copy() {
        return new CreatureConnivesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CONNIVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // 701.50b If a permanent is no longer on the battlefield as an effect instructs it to connive, its last known
        // information is used to determine which object connived and who controlled it.
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game) || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        if (setTargetPointer == SetTargetPointer.PERMANENT) {
            getEffects().setTargetPointer(new FixedTarget(permanent, game));
        }
        return true;
    }
}
