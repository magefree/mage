
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public class DealtDamageAttachedTriggeredAbility extends TriggeredAbilityImpl {

    protected SetTargetPointer setTargetPointer;

    public DealtDamageAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional, SetTargetPointer.NONE);
    }

    public DealtDamageAttachedTriggeredAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever enchanted creature is dealt damage, ");
    }

    public DealtDamageAttachedTriggeredAbility(final DealtDamageAttachedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealtDamageAttachedTriggeredAbility copy() {
        return new DealtDamageAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        UUID targetId = event.getTargetId();
        if(enchantment != null && enchantment.getAttachedTo() != null && targetId.equals(enchantment.getAttachedTo())) {
            for(Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
                switch(setTargetPointer) {
                    case PERMANENT:
                        effect.setTargetPointer(new FixedTarget(targetId, game));
                        break;
                    case PLAYER:
                        effect.setTargetPointer(new FixedTarget(game.getPermanentOrLKIBattlefield(targetId).getControllerId()));
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
