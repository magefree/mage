package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

public class SkipUntapSourceEffect extends ReplacementEffectImpl<SkipUntapSourceEffect> {

    public SkipUntapSourceEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "{this} doesn't untap during your next untap step";
    }

    public SkipUntapSourceEffect(final SkipUntapSourceEffect effect) {
        super(effect);
    }

    @Override
    public SkipUntapSourceEffect copy() {
        return new SkipUntapSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

}