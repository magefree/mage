package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class DealsCombatDamageToAPlayerAttachedTriggeredAbility extends TriggeredAbilityImpl<DealsCombatDamageToAPlayerAttachedTriggeredAbility> {
    private boolean setFixedTargetPointer;
    private String attachedDescription;

    public DealsCombatDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, false);
    }

    public DealsCombatDamageToAPlayerAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
        this.setFixedTargetPointer = setFixedTargetPointer;
        this.attachedDescription = attachedDescription;
    }

    public DealsCombatDamageToAPlayerAttachedTriggeredAbility(final DealsCombatDamageToAPlayerAttachedTriggeredAbility ability) {
        super(ability);
        this.setFixedTargetPointer = ability.setFixedTargetPointer;
        this.attachedDescription = ability.attachedDescription;
    }

    @Override
    public DealsCombatDamageToAPlayerAttachedTriggeredAbility copy() {
        return new DealsCombatDamageToAPlayerAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
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
        return "Whenever " + attachedDescription + " creature deals combat damage to a player, " + super.getRule();
    }
}
