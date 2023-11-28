
package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.common.DamagedByWatcher;

/**
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

    protected DealtDamageToCreatureBySourceDies(final DealtDamageToCreatureBySourceDies effect) {
        super(effect);
    }

    @Override
    public DealtDamageToCreatureBySourceDies copy() {
        return new DealtDamageToCreatureBySourceDies(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }

}
