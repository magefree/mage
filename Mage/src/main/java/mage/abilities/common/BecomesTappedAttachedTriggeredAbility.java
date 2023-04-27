
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 */
public class BecomesTappedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesTappedAttachedTriggeredAbility(Effect effect, String description) {
        this(effect, description, false);
    }

    public BecomesTappedAttachedTriggeredAbility(Effect effect, String description, boolean isOptional) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        setTriggerPhrase("Whenever " + description + " becomes tapped, ");
    }

    public BecomesTappedAttachedTriggeredAbility(final BecomesTappedAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTappedAttachedTriggeredAbility copy() {
        return new BecomesTappedAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if(enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        return enchanted != null && event.getTargetId().equals(enchanted.getId());
    }
}
