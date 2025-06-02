package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class EnterUntappedAllEffect extends ReplacementEffectImpl {

    private final FilterPermanent filter;

    public EnterUntappedAllEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        staticText = filter.getMessage() + " enter untapped";
    }

    private EnterUntappedAllEffect(final EnterUntappedAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public EnterUntappedAllEffect copy() {
        return new EnterUntappedAllEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(false);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent targetObject = ((EntersTheBattlefieldEvent) event).getTarget();
        return targetObject != null && filter.match(targetObject, source.getControllerId(), source, game);
    }
}
