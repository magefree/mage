package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class DealsDamageToAPlayerAttachedTriggeredAbility extends TriggeredAbilityImpl<DealsDamageToAPlayerAttachedTriggeredAbility> {
    private boolean setFixedTargetPointer;
    private String attachedDescription;
    private boolean onlyCombat;
    private TargetController targetController;

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, false);
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer) {
        this(effect, attachedDescription, optional, setFixedTargetPointer, true);
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer, boolean onlyCombat) {
        this(effect, attachedDescription, optional, setFixedTargetPointer, onlyCombat, TargetController.ANY);
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer, boolean onlyCombat, TargetController targetController) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setFixedTargetPointer = setFixedTargetPointer;
        this.attachedDescription = attachedDescription;
        this.targetController = targetController;
        this.onlyCombat = onlyCombat;
    }

    public DealsDamageToAPlayerAttachedTriggeredAbility(final DealsDamageToAPlayerAttachedTriggeredAbility ability) {
        super(ability);
        this.setFixedTargetPointer = ability.setFixedTargetPointer;
        this.attachedDescription = ability.attachedDescription;
        this.onlyCombat = ability.onlyCombat;
        this.targetController = ability.targetController;
    }

    @Override
    public DealsDamageToAPlayerAttachedTriggeredAbility copy() {
        return new DealsDamageToAPlayerAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            if (targetController.equals(TargetController.OPPONENT)) {
                Player controller = game.getPlayer(this.getControllerId());
                if (controller == null || !game.isOpponent(controller, event.getPlayerId())) {
                    return false;
                }
            }
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
        if (onlyCombat) {
            sb.append(" combat");
        }
        sb.append(" damage to ");
        switch(targetController) {
            case OPPONENT:
                sb.append("an opponent, ");
                break;
            case ANY:
                sb.append("a player, ");
                break;
            default:
                throw new UnsupportedOperationException();
        }
        sb.append(super.getRule());
        return  sb.toString();
    }
}
