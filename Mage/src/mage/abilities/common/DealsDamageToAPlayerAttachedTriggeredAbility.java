package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class DealsDamageToAPlayerAttachedTriggeredAbility extends TriggeredAbilityImpl<DealsDamageToAPlayerAttachedTriggeredAbility> {
    private boolean setFixedTargetPointer;
    private String attachedDescription;
    private boolean onlyCombat;

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, false);
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer) {
        this(effect, attachedDescription, optional, setFixedTargetPointer, true);
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer, boolean onlyCombat) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setFixedTargetPointer = setFixedTargetPointer;
        this.attachedDescription = attachedDescription;
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(final DealsDamageToAPlayerAttachedTriggeredAbility ability) {
        super(ability);
        this.setFixedTargetPointer = ability.setFixedTargetPointer;
        this.attachedDescription = ability.attachedDescription;
        this.onlyCombat = ability.onlyCombat;
    }

    @Override
    public DealsDamageToAPlayerAttachedTriggeredAbility copy() {
        return new DealsDamageToAPlayerAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if ((!onlyCombat || damageEvent.isCombatDamage())
                    && p != null && p.getAttachments().contains(this.getSourceId())) {
                if (setFixedTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Whenever ").append(attachedDescription);
        sb.append(" deals");
        if (!onlyCombat) {
            sb.append(" combat");
        }
        sb.append(" damage to a player, ").append(super.getRule());
        return  sb.toString();
    }
}
