
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 */
public class BecomesTargetAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final String enchantType;

    public BecomesTargetAttachedTriggeredAbility(Effect effect) {
        this(effect, "creature");
    }

    public BecomesTargetAttachedTriggeredAbility(Effect effect, String enchantType) {
        super(Zone.BATTLEFIELD, effect);
        this.enchantType = enchantType;
    }

    public BecomesTargetAttachedTriggeredAbility(final BecomesTargetAttachedTriggeredAbility ability) {
        super(ability);
        this.enchantType = ability.enchantType;
    }

    @Override
    public BecomesTargetAttachedTriggeredAbility copy() {
        return new BecomesTargetAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getTargetId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted " + enchantType + " becomes the target of a spell or ability, " + super.getRule();
    }
}
