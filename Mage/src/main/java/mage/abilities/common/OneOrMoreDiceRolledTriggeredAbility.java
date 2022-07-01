package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DiceRolledEvent;
import mage.game.events.GameEvent;

/**
 * Controller rolls one or more dice.
 *
 * @author weirddan455
 */
public class OneOrMoreDiceRolledTriggeredAbility extends TriggeredAbilityImpl {

    public OneOrMoreDiceRolledTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public OneOrMoreDiceRolledTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    private OneOrMoreDiceRolledTriggeredAbility(final OneOrMoreDiceRolledTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public OneOrMoreDiceRolledTriggeredAbility copy() {
        return new OneOrMoreDiceRolledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getTargetId())) {
            return false;
        }
        int maxRoll = ((DiceRolledEvent) event)
                .getResults()
                .stream()
                .filter(Integer.class::isInstance) // only numerical die result can be masured
                .map(Integer.class::cast)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        int totalRoll = ((DiceRolledEvent) event)
                .getResults()
                .stream()
                .filter(Integer.class::isInstance) // only numerical die result can be masured
                .map(Integer.class::cast)
                .mapToInt(Integer::intValue)
                .sum();
        this.getEffects().setValue("maxDieRoll", maxRoll);
        this.getEffects().setValue("totalDieRoll", totalRoll);
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you roll one or more dice, ";
    }

    @Override
    public String getRule() {
        return super.getRule();
    }
}
