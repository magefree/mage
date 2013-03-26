package mage.abilities.common;

import mage.Constants.Zone;
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
    private boolean diesRuleText;
    
    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription) {
        this(effect, attachedDescription, false);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, true);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean diesRuleText) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.attachedDescription = attachedDescription;
        this.diesRuleText = diesRuleText;
    }


    public DiesAttachedTriggeredAbility(final DiesAttachedTriggeredAbility ability) {
        super(ability);
        this.attachedDescription = ability.attachedDescription;
        this.diesRuleText = ability.diesRuleText;
    }

    @Override
    public DiesAttachedTriggeredAbility copy() {
        return new DiesAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
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
        StringBuilder sb = new StringBuilder("When ").append(attachedDescription);
        if (diesRuleText) {
            sb.append(" dies, ");
        } else {
            sb.append(" is put into graveyard, ");
        }
        sb.append(super.getRule());
        return sb.toString();
    }
}
