package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.StaticHint;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class GainLifeControllerTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;
    private final boolean showAmountGainedHint;

    public GainLifeControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public GainLifeControllerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public GainLifeControllerTriggeredAbility(Effect effect, boolean optional, boolean showAmountGained) {
        this(effect, optional, showAmountGained, false);
    }

    public GainLifeControllerTriggeredAbility(Effect effect, boolean optional, boolean showAmountGained, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, showAmountGained, setTargetPointer);
    }

    public GainLifeControllerTriggeredAbility(Zone zone, Effect effect, boolean optional, boolean showAmountGained, boolean setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.showAmountGainedHint = showAmountGained;
        setTriggerPhrase("Whenever you gain life, ");
    }

    private GainLifeControllerTriggeredAbility(final GainLifeControllerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.showAmountGainedHint = ability.showAmountGainedHint;
    }

    @Override
    public GainLifeControllerTriggeredAbility copy() {
        return new GainLifeControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getHints().clear();
        this.getEffects().setValue(SavedGainedLifeValue.VALUE_KEY, event.getAmount());
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        if (showAmountGainedHint) {
            this.addHint(new StaticHint("Life gained: " + event.getAmount()));
        }
        return true;
    }
}
