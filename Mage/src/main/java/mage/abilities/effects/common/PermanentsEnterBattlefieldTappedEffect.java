package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Eirkei
 */
public class PermanentsEnterBattlefieldTappedEffect extends ReplacementEffectImpl {

    protected final FilterPermanent filter;

    public PermanentsEnterBattlefieldTappedEffect(FilterPermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    public PermanentsEnterBattlefieldTappedEffect(FilterPermanent filter, Duration duration) {
        super(duration, Outcome.Tap);
        this.filter = filter;
    }

    public PermanentsEnterBattlefieldTappedEffect(final PermanentsEnterBattlefieldTappedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.tap(source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return filter.match(permanent, source.getSourceId(), event.getPlayerId(), game);
    }

    @Override
    public PermanentsEnterBattlefieldTappedEffect copy() {
        return new PermanentsEnterBattlefieldTappedEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return filter.getMessage()
                + " enter the battlefield tapped"
                + (duration == Duration.EndOfTurn ? " this turn" : "");
    }
}
