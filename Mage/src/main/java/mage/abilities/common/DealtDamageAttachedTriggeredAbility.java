package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LoneFox
 */
public class DealtDamageAttachedTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    private final SetTargetPointer setTargetPointer;

    public DealtDamageAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional, SetTargetPointer.NONE);
    }

    public DealtDamageAttachedTriggeredAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + "enchanted creature is dealt damage, ");
    }

    protected DealtDamageAttachedTriggeredAbility(final DealtDamageAttachedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealtDamageAttachedTriggeredAbility copy() {
        return new DealtDamageAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        Permanent enchantment = getSourcePermanentOrLKI(game);
        UUID targetId = event.getTargetId();
        if (enchantment != null && enchantment.getAttachedTo() != null && targetId.equals(enchantment.getAttachedTo())) {
            getEffects().setValue("damage", event.getAmount());
            switch (setTargetPointer) {
                case PERMANENT:
                    getEffects().setTargetPointer(new FixedTarget(targetId, game));
                    return true;
                case PLAYER:
                    getEffects().setTargetPointer(new FixedTarget(enchantment.getControllerId()));
                    return true;
                default:
                    throw new IllegalArgumentException("Unsupported SetTargetPointer in DealtDamageAttachedTriggeredAbility");
            }
        }
        return false;
    }
}
