/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */

 public class DiscardsACardPlayerTriggeredAbility extends TriggeredAbilityImpl {

    private SetTargetPointer setTargetPointer;

    public DiscardsACardPlayerTriggeredAbility(Effect effect, boolean isOptional) {
        this(effect, isOptional, SetTargetPointer.NONE);
    }

    public DiscardsACardPlayerTriggeredAbility(Effect effect, boolean isOptional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        this.setTargetPointer = setTargetPointer;
    }

    public DiscardsACardPlayerTriggeredAbility(final DiscardsACardPlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiscardsACardPlayerTriggeredAbility copy() {
        return new DiscardsACardPlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever player discards a card, " + super.getRule();
    }
}