package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author jimga150
 */
public class LifeLostEvent extends GameEvent {

    public LifeLostEvent(UUID playerId, Ability source, int amount, boolean atCombat){
        super(GameEvent.EventType.LOST_LIFE, playerId, source, playerId, amount, atCombat);
    }

}
