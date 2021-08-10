
package mage.abilities.common;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPermanentBatchEvent;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean useValue;

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage) {
        this(effect, optional, enrage, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage, boolean useValue) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.useValue = useValue;
        if (enrage) {
            this.setAbilityWord(AbilityWord.ENRAGE);
        }
    }

    public DealtDamageToSourceTriggeredAbility(final DealtDamageToSourceTriggeredAbility ability) {
        super(ability);
        this.useValue = ability.useValue;
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

        if (event == null || game == null || this.getSourceId() == null) {
            return false;
        }

        int damage = 0;
        DamagedPermanentBatchEvent dEvent = (DamagedPermanentBatchEvent) event;
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            UUID targetID = damagedEvent.getTargetId();
            if (targetID == null) {
                continue;
            }

            if (targetID == this.getSourceId()) {
                damage += damagedEvent.getAmount();
            }
        }

        if (damage > 0) {
            if (this.useValue) {
                this.getEffects().setValue("damage", damage);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} is dealt damage, ";
    }
}
