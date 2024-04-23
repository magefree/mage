package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.stream.Stream;

/**
 * @author Susucr
 */
public class IsDealtDamageYouTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    public IsDealtDamageYouTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever you're dealt damage, ");
    }

    private IsDealtDamageYouTriggeredAbility(final IsDealtDamageYouTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IsDealtDamageYouTriggeredAbility copy() {
        return new IsDealtDamageYouTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public Stream<DamagedPlayerEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForOnePlayerEvent) event)
                .getEvents()
                .stream()
                .filter(e -> getControllerId().equals(e.getTargetId()))
                .filter(e -> e.getAmount() > 0);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = filterBatchEvent(event, game)
                .mapToInt(DamagedPlayerEvent::getAmount)
                .sum();
        if (amount <= 0) {
            return false;
        }
        getEffects().setValue("damage", amount);
        return true;
    }
}
