
package mage.abilities.common;

import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LoneFox
 */
public class BecomesTappedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final SetTargetPointer setTargetPointer;

    public BecomesTappedAttachedTriggeredAbility(Effect effect, String description) {
        this(effect, description, false);
    }

    public BecomesTappedAttachedTriggeredAbility(Effect effect, String description, boolean isOptional) {
        this(effect, description, isOptional, SetTargetPointer.NONE);
    }

    public BecomesTappedAttachedTriggeredAbility(Effect effect, String description, boolean isOptional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        setTriggerPhrase(getWhen() + description + " becomes tapped, ");
        this.setTargetPointer = setTargetPointer;
    }

    protected BecomesTappedAttachedTriggeredAbility(final BecomesTappedAttachedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
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
        if (enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        if (enchanted == null || !event.getTargetId().equals(enchanted.getId())) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(enchanted, game));
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in BecomesTappedAttachedTriggeredAbility");
        }
        return true;
    }
}
