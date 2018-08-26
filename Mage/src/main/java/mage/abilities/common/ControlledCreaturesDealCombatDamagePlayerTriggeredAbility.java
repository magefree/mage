
package mage.abilities.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ControlledCreaturesDealCombatDamagePlayerTriggeredAbility extends TriggeredAbilityImpl {

    private Set<UUID> damagedPlayerIds = new HashSet<>();
    private boolean setTargetPointer;

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect) {
        this(zone, effect, false);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect, boolean setTargetPointer) {
        super(zone, effect, false);
        this.setTargetPointer = setTargetPointer;
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(final ControlledCreaturesDealCombatDamagePlayerTriggeredAbility ability) {
        super(ability);
        this.damagedPlayerIds = new HashSet<>();
    }

    @Override
    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility copy() {
        return new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.isControlledBy(this.getControllerId()) && !damagedPlayerIds.contains(event.getPlayerId())) {
                damagedPlayerIds.add(event.getPlayerId());
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            }
        }
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_PRIORITY ||
                (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId()))) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player, " + super.getRule();
    }
}
