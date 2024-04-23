package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForPermanentsEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;

import java.util.stream.Stream;

/**
 * @author LevelX2
 */
// TODO Susucr: rename to IsDealtDamageSourceTriggeredAbility after merge for some consistency.
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    public DealtDamageToSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PERMANENTS;
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForPermanentsEvent) event)
                .getEvents()
                .stream()
                .filter(damagedEvent -> getSourceId().equals(damagedEvent.getTargetId())
                        && damagedEvent.getAmount() > 0);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int damage = filterBatchEvent(event, game)
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damage);
        return true;
    }
}
