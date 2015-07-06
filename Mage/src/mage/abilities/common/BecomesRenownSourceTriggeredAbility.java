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

/**
 *
 * @author LevelX2
 */
public class BecomesRenownSourceTriggeredAbility extends TriggeredAbilityImpl {

    private int renownValue;

    public BecomesRenownSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesRenownSourceTriggeredAbility(final BecomesRenownSourceTriggeredAbility ability) {
        super(ability);
        this.renownValue = ability.renownValue;
    }

    @Override
    public BecomesRenownSourceTriggeredAbility copy() {
        return new BecomesRenownSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_RENOWN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            this.renownValue = event.getAmount();
            return true;
        }
        return false;
    }

    public int getRenownValue() {
        return renownValue;
    }

    @Override
    public String getRule() {
        return "When {this} becomes renown, " + super.getRule();
    }
}
