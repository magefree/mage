
package mage.abilities;

import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StaticAbility extends AbilityImpl {

    protected StaticAbility(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
    }

    public StaticAbility(Zone zone, Effect effect) {
        super(AbilityType.STATIC, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        if (game.getShortLivingLKI(getSourceId(), zone)) {
            return true; // maybe this can be a problem if effects removed the ability from the object
        }
        if (game.getPermanentEntering(getSourceId()) != null && zone == Zone.BATTLEFIELD) {
            return true; // abilities of permanents entering battlefield are countes as on battlefield
        }
        return super.isInUseableZone(game, source, event);
    }

    protected StaticAbility(final StaticAbility ability) {
        super(ability);
    }
}
