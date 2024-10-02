package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class AttacksAndIsNotBlockedAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public AttacksAndIsNotBlockedAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter);
    }

    public AttacksAndIsNotBlockedAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter) {
        this(zone, effect, filter, false, SetTargetPointer.NONE);
    }

    public AttacksAndIsNotBlockedAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + filter.getMessage() + " attacks and isn't blocked, ");
    }

    private AttacksAndIsNotBlockedAllTriggeredAbility(final AttacksAndIsNotBlockedAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksAndIsNotBlockedAllTriggeredAbility copy() {
        return new AttacksAndIsNotBlockedAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case NONE:
                break;
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(
                        game.getCombat().getDefendingPlayerId(event.getTargetId(), game), game
                ));
                break;
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: not supported setTargetPointer");
        }
        return true;
    }
}
