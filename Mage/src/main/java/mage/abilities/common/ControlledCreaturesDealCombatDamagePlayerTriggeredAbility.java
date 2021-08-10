package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ControlledCreaturesDealCombatDamagePlayerTriggeredAbility extends TriggeredAbilityImpl {

    private final Set<UUID> damagedPlayerIds = new HashSet<>();
    private final boolean setTargetPointer;
    private final boolean onlyOpponents;

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect) {
        this(zone, effect, false);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect, boolean setTargetPointer) {
        this(zone, effect, setTargetPointer, false);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect, boolean setTargetPointer, boolean onlyOpponents) {
        super(zone, effect, false);
        this.setTargetPointer = setTargetPointer;
        this.onlyOpponents = onlyOpponents;
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(final ControlledCreaturesDealCombatDamagePlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.onlyOpponents = ability.onlyOpponents;
    }

    @Override
    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility copy() {
        return new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY ||
                (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId()))) {
            damagedPlayerIds.clear();
            return false;
        }
        if (event.getType() != EventType.DAMAGED_PLAYER) {
            return false;
        }
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (!damageEvent.isCombatDamage()
                || p == null
                || !p.isControlledBy(this.getControllerId())
                || damagedPlayerIds.contains(event.getPlayerId())
                || (onlyOpponents && !game.getOpponents(getControllerId()).contains(event.getPlayerId()))) {
            return false;
        }
        damagedPlayerIds.add(event.getPlayerId());
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever one or more creatures you control deal combat damage to "
                + (onlyOpponents ? "an opponent" : "a player") + ", " ;
    }
}
