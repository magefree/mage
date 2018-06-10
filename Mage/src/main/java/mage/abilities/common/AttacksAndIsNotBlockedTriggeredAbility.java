
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class AttacksAndIsNotBlockedTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public AttacksAndIsNotBlockedTriggeredAbility(final AttacksAndIsNotBlockedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksAndIsNotBlockedTriggeredAbility copy() {
        return new AttacksAndIsNotBlockedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARE_BLOCKERS_STEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (sourcePermanent != null && sourcePermanent.isAttacking()) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(getSourceId())) {
                    if (setTargetPointer) {
                        this.getEffects().setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(getSourceId(), game)));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks and isn't blocked, " + super.getRule();
    }
}
