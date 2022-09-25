
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AttackedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected SetTargetPointer setTargetPointer;
    protected FilterCreaturePermanent filter;

    public AttackedByCreatureTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttackedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public AttackedByCreatureTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, setTargetPointer);
    }

    public AttackedByCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        this(zone, effect, optional, setTargetPointer, StaticFilters.FILTER_PERMANENT_A_CREATURE);
    }

    public AttackedByCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer, FilterCreaturePermanent filter) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        setTriggerPhrase("Whenever " + filter.getMessage() + " attacks you, ");
    }

    public AttackedByCreatureTriggeredAbility(final AttackedByCreatureTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defendingPlayer = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
        Permanent attackingCreature = game.getPermanent(event.getSourceId());
        if (filter.match(attackingCreature, game)
                && isControlledBy(defendingPlayer)
                && attackingCreature != null) {
            switch (setTargetPointer) {
                case PERMANENT:
                    this.getEffects().setTargetPointer(new FixedTarget(attackingCreature, game));
                    break;
                case PLAYER:
                    this.getEffects().setTargetPointer(new FixedTarget(attackingCreature.getControllerId()));
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public AttackedByCreatureTriggeredAbility copy() {
        return new AttackedByCreatureTriggeredAbility(this);
    }
}
