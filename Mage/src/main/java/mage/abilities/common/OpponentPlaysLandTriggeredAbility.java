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
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class OpponentPlaysLandTriggeredAbility extends TriggeredAbilityImpl {

    public OpponentPlaysLandTriggeredAbility(Zone zone, Effect effect, Boolean optional) {
        super(zone, effect, optional);
    }

    public OpponentPlaysLandTriggeredAbility(OpponentPlaysLandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return game.getOpponents(controllerId).contains(land.getControllerId());
    }

    @Override
    public OpponentPlaysLandTriggeredAbility copy() {
        return new OpponentPlaysLandTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent plays a land, ";
    }
}
