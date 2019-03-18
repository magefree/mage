

package mage.abilities.abilityword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * Constellation
 *
 *
 * @author LevelX2
 */

public class ConstellationAbility extends TriggeredAbilityImpl {

    public ConstellationAbility(Effect effect) {
        this(effect, false);
    }

    public ConstellationAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public ConstellationAbility(final ConstellationAbility ability) {
        super(ability);
    }

    @Override
    public ConstellationAbility copy() {
        return new ConstellationAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isEnchantment()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("<i>Constellation</i> &mdash; Whenever {this} or another enchantment enters the battlefield under your control, ").append(super.getRule()).toString();
    }
}
