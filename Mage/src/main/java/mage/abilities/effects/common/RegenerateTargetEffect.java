package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author maurer.it_at_gmail.com
 */
public class RegenerateTargetEffect extends ReplacementEffectImpl {

    public RegenerateTargetEffect() {
        super(Duration.EndOfTurn, Outcome.Regenerate);
    }

    protected RegenerateTargetEffect(final RegenerateTargetEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        RegenerateSourceEffect.initRegenerationShieldInfo(game, source, getTargetPointer().getFirst(game, source));
    }

    @Override
    public RegenerateTargetEffect copy() {
        return new RegenerateTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.regenerate(source, game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.DESTROY_PERMANENT == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        return event.getAmount() == 0 && event.getTargetId().equals(getTargetPointer().getFirst(game, source)) && !this.used;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "regenerate " + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
