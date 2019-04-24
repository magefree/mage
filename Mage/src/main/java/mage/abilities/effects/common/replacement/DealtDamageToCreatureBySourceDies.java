
package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public class DealtDamageToCreatureBySourceDies extends ReplacementEffectImpl {

    public DealtDamageToCreatureBySourceDies(Card card, Duration duration) {
        super(duration, Outcome.Exile);
        if (card.isCreature()) {
            staticText = "If a creature dealt damage by {this} this turn would die, exile it instead";
        } else {
            staticText = "If a creature dealt damage this way would die this turn, exile it instead";
        }
    }

    public DealtDamageToCreatureBySourceDies(final DealtDamageToCreatureBySourceDies effect) {
        super(effect);
    }

    @Override
    public DealtDamageToCreatureBySourceDies copy() {
        return new DealtDamageToCreatureBySourceDies(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            return controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get(DamagedByWatcher.class.getSimpleName(), source.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }

}
