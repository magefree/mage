
package mage.abilities.effects.common;

import java.util.Locale;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class RegenerateTargetEffect extends ReplacementEffectImpl {

    public RegenerateTargetEffect() {
        super(Duration.EndOfTurn, Outcome.Regenerate);
    }

    public RegenerateTargetEffect(final RegenerateTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && permanent.regenerate(this.getId(), game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public RegenerateTargetEffect copy() {
        return new RegenerateTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.DESTROY_PERMANENT == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed

        return event.getAmount() == 0 && event.getTargetId().equals(targetPointer.getFirst(game, source)) && !this.used;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Regenerate ");
        Target target = mode.getTargets().get(0);
        if (target != null) {
            if (!target.getTargetName().toLowerCase(Locale.ENGLISH).startsWith("another")) {
                sb.append("target ");
            }
            sb.append(target.getTargetName());
        }
        return sb.toString();
    }

}
