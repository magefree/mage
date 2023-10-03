package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author xenohedron
 */
public class LeaveBattlefieldExileTargetReplacementEffect extends ReplacementEffectImpl {

    public LeaveBattlefieldExileTargetReplacementEffect(String description) {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "If " + description + " would leave the battlefield, exile it instead of putting it anywhere else";
    }

    protected LeaveBattlefieldExileTargetReplacementEffect(final LeaveBattlefieldExileTargetReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LeaveBattlefieldExileTargetReplacementEffect copy() {
        return new LeaveBattlefieldExileTargetReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(event.getTargetId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED;
    }

}
