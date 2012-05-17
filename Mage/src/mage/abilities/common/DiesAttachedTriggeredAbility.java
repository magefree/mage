package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * "When enchanted/equipped creature dies" triggered ability
 * @author Loki
 */
public class DiesAttachedTriggeredAbility extends TriggeredAbilityImpl<DiesAttachedTriggeredAbility> {
    private String attachedDescription;

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription) {
        super(Constants.Zone.BATTLEFIELD, effect);
        this.attachedDescription = attachedDescription;
    }

    public DiesAttachedTriggeredAbility(final DiesAttachedTriggeredAbility ability) {
        super(ability);
        this.attachedDescription = ability.attachedDescription;
    }

    @Override
    public DiesAttachedTriggeredAbility copy() {
        return new DiesAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (p.getAttachments().contains(this.getSourceId())) {
                for (Effect effect : getEffects()) {
                    effect.setValue("attachedTo", p);
                }
                return true;
            }
		}
		return false;
    }

    @Override
    public String getRule() {
        return "When " + attachedDescription + " dies, " + super.getRule();
    }
}
