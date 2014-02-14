/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author jeffwadsworth
 */
public class DiscardsACardOpponentTriggeredAbility extends TriggeredAbilityImpl<DiscardsACardOpponentTriggeredAbility> {

    public DiscardsACardOpponentTriggeredAbility(Effect effect, Boolean isOptional) {
        super(Zone.BATTLEFIELD, effect, isOptional);
    }

    public DiscardsACardOpponentTriggeredAbility(final DiscardsACardOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiscardsACardOpponentTriggeredAbility copy() {
        return new DiscardsACardOpponentTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DISCARDED_CARD && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a card, " + super.getRule();
    }
}
