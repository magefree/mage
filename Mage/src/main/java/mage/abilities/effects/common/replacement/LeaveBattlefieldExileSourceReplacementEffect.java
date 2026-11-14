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
public class LeaveBattlefieldExileSourceReplacementEffect extends ReplacementEffectImpl {

    public LeaveBattlefieldExileSourceReplacementEffect(String description) {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If " + description + " would leave the battlefield, exile it instead of putting it anywhere else";
    }

    protected LeaveBattlefieldExileSourceReplacementEffect(final LeaveBattlefieldExileSourceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LeaveBattlefieldExileSourceReplacementEffect copy() {
        return new LeaveBattlefieldExileSourceReplacementEffect(this);
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
        // 400.7: from a resolved ability, only the object that ability knew
        return source.getSourceId().equals(event.getTargetId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED
                && source.getSourcePermanentIfItStillExists(game) != null;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        // an ability creating this (Duration.Custom) ends it once the object it knew is gone
        return super.isInactive(source, game) || source.getSourcePermanentIfItStillExists(game) == null;
    }

}
