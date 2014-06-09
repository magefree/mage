/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author LevelX2
 */

public class TurnedFaceUpTriggeredAbility extends TriggeredAbilityImpl {

    public TurnedFaceUpTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public TurnedFaceUpTriggeredAbility(final TurnedFaceUpTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TurnedFaceUpTriggeredAbility copy() {
        return new TurnedFaceUpTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return EventType.TURNEDFACEUP.equals(event.getType()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When {this} is turned face up, " + super.getRule();
    }
}
