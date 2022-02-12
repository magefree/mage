package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPermanentBatchEvent;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl {

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage) {
        super(Zone.BATTLEFIELD, effect, optional);
        if (enrage) {
            this.setAbilityWord(AbilityWord.ENRAGE);
        }
    }

    public DealtDamageToSourceTriggeredAbility(final DealtDamageToSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtDamageToSourceTriggeredAbility copy() {
        return new DealtDamageToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPermanentBatchEvent dEvent = (DamagedPermanentBatchEvent) event;
        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> getSourceId().equals(damagedEvent.getTargetId()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damage);
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} is dealt damage, ";
    }
}
