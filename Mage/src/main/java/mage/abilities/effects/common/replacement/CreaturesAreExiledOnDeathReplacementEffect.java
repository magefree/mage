package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author Susucr
 */
public class CreaturesAreExiledOnDeathReplacementEffect extends ReplacementEffectImpl {

    private FilterPermanent filter;

    public CreaturesAreExiledOnDeathReplacementEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If " + CardUtil.addArticle(filter.getMessage()) + " would die, exile it instead";
        this.filter = filter;
    }

    private CreaturesAreExiledOnDeathReplacementEffect(final CreaturesAreExiledOnDeathReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CreaturesAreExiledOnDeathReplacementEffect copy() {
        return new CreaturesAreExiledOnDeathReplacementEffect(this);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }

        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }

        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
