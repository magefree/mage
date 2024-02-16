package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LeavesBattlefieldTriggeredAbility extends ZoneChangeTriggeredAbility {

    public LeavesBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, Zone.BATTLEFIELD, null, effect, "When {this} leaves the battlefield, ", optional);
    }

    protected LeavesBattlefieldTriggeredAbility(LeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            for (Effect effect : getEffects()) {
                effect.setValue("permanentLeftBattlefield", ((ZoneChangeEvent) event).getTarget());
            }
            return true;
        }
        return false;
    }

    @Override
    public LeavesBattlefieldTriggeredAbility copy() {
        return new LeavesBattlefieldTriggeredAbility(this);
    }

}
