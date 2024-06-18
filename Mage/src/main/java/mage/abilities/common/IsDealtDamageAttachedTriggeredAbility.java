
package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author LoneFox
 */
public class IsDealtDamageAttachedTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    protected SetTargetPointer setTargetPointer;

    public IsDealtDamageAttachedTriggeredAbility(Effect effect, boolean optional, String attachedDescription) {
        this(Zone.BATTLEFIELD, effect, optional, attachedDescription, SetTargetPointer.NONE);
    }

    public IsDealtDamageAttachedTriggeredAbility(Zone zone, Effect effect, boolean optional, String attachedDescription, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + attachedDescription + " creature is dealt damage, ");
    }

    protected IsDealtDamageAttachedTriggeredAbility(final IsDealtDamageAttachedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public IsDealtDamageAttachedTriggeredAbility copy() {
        return new IsDealtDamageAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        Permanent attachment = game.getPermanent(sourceId);
        if (attachment == null) {
            return Stream.empty();
        }
        UUID targetId = event.getTargetId();
        UUID enchantedToId = attachment.getAttachedTo();
        if (enchantedToId == null || !targetId.equals(enchantedToId)) {
            return Stream.empty();
        }
        return ((DamagedBatchForOnePermanentEvent) event)
                .getEvents()
                .stream()
                .filter(e -> e.getAmount() > 0);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = filterBatchEvent(event, game)
                .mapToInt(DamagedPermanentEvent::getAmount)
                .sum();
        if (amount <= 0) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case PLAYER:
                Permanent enchanted = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (enchanted == null) {
                    return false;
                }
                getEffects().setTargetPointer(new FixedTarget(enchanted.getControllerId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: setTargetPointer not expected: " + setTargetPointer);
        }
        getEffects().setValue("damage", amount);
        return true;
    }
}
