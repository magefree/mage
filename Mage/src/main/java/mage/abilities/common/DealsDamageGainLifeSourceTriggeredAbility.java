package mage.abilities.common;

import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Skiwkr
 */

public class DealsDamageGainLifeSourceTriggeredAbility extends DealsDamageTriggeredAbility {

    public DealsDamageGainLifeSourceTriggeredAbility() {
        super(new GainLifeEffect(SavedDamageValue.MUCH));
        setTriggerPhrase("Whenever {this} deals damage, ");
    }

    protected DealsDamageGainLifeSourceTriggeredAbility(final DealsDamageGainLifeSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageGainLifeSourceTriggeredAbility copy() {
        return new DealsDamageGainLifeSourceTriggeredAbility(this);
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
}
