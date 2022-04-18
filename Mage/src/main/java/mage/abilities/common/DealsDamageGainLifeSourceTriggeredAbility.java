package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */

public class DealsDamageGainLifeSourceTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageGainLifeSourceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(SavedDamageValue.MUCH), false);
    }

    public DealsDamageGainLifeSourceTriggeredAbility(final DealsDamageGainLifeSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageGainLifeSourceTriggeredAbility copy() {
        return new DealsDamageGainLifeSourceTriggeredAbility(this);
    }
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() ==  GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals damage, " ;
    }
}
