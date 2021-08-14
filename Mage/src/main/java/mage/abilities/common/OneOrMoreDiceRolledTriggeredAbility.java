package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author weirddan455
 */
public class OneOrMoreDiceRolledTriggeredAbility extends TriggeredAbilityImpl {

    public OneOrMoreDiceRolledTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
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
        return event.getPlayerId().equals(getControllerId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you roll one or more dice, ";
    }
}
