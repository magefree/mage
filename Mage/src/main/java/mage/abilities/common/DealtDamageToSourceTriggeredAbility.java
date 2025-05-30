package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage) {
        super(Zone.BATTLEFIELD, effect, optional);
        if (enrage) {
            this.setAbilityWord(AbilityWord.ENRAGE);
        }
        setTriggerPhrase("Whenever {this} is dealt damage, ");
        this.withRuleTextReplacement(true);
    }

    protected DealtDamageToSourceTriggeredAbility(final DealtDamageToSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtDamageToSourceTriggeredAbility copy() {
        return new DealtDamageToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        if (!getSourceId().equals(event.getTargetId())) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
