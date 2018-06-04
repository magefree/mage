

package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RegenerateSourceEffect extends ReplacementEffectImpl {

    public RegenerateSourceEffect() {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        staticText = "Regenerate {this}";
    }

    public RegenerateSourceEffect(final RegenerateSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.regenerate(this.getId(), game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public RegenerateSourceEffect copy() {
        return new RegenerateSourceEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        return event.getAmount() == 0 && event.getTargetId().equals(source.getSourceId()) && !this.used;
    }

}
