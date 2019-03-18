
package mage.abilities.common;

import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DiesTriggeredAbility extends ZoneChangeTriggeredAbility {

    public DiesTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, "When {this} dies, ", optional);
    }

    public DiesTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesTriggeredAbility(DiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        // check it was previously on battlefield
        Permanent before = ((ZoneChangeEvent) event).getTarget();
        if (before == null) {
            return false;
        }
        if (!(before instanceof PermanentToken) && !this.hasSourceObjectAbility(game, before, event)) {
            return false;
        }
        // check now it is in graveyard
        if (before.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(sourceId)) {
            Zone after = game.getState().getZone(sourceId);
            return after != null && Zone.GRAVEYARD.match(after);
        } else {
            // Already moved to another zone, so guess it's ok
            return true;
        }
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (super.checkEventType(event, game)) {
            return ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
        }
        return false;
    }

    @Override
    public DiesTriggeredAbility copy() {
        return new DiesTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getTarget().isTransformable()) {
                if (!zEvent.getTarget().getAbilities().contains(this)) {
                    return false;
                }
            }
            for (Effect effect : getEffects()) {
                effect.setValue("permanentLeftBattlefield", zEvent.getTarget());
            }
            return true;
        }
        return false;
    }

}
