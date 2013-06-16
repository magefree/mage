package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;

public class SkipNextUntapSourceEffect extends ReplacementEffectImpl<SkipNextUntapSourceEffect> {

    public SkipNextUntapSourceEffect() {
        super(Duration.OneUse, Outcome.Detriment);
        staticText = "{this} doesn't untap during your next untap step";
    }

    public SkipNextUntapSourceEffect(final SkipNextUntapSourceEffect effect) {
        super(effect);
    }

    @Override
    public SkipNextUntapSourceEffect copy() {
        return new SkipNextUntapSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        used = true;
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

}