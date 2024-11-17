package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

/**
 * @author xenohedron
 */
public class YoureDealtDamageTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    public YoureDealtDamageTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public YoureDealtDamageTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you're dealt damage, ");
    }

    protected YoureDealtDamageTriggeredAbility(final YoureDealtDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public YoureDealtDamageTriggeredAbility copy() {
        return new YoureDealtDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant
        if (isControlledBy(event.getTargetId())) {
            this.getAllEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

}
