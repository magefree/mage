package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;

public class SkipNextUntapSourceEffect extends ContinuousRuleModifiyingEffectImpl {

    private int validForTurnNum;
    
    public SkipNextUntapSourceEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "{this} doesn't untap during your next untap step";
        validForTurnNum = 0;
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
    public boolean applies(GameEvent event, Ability source, boolean checkPlayableMode, Game game) {
        if (validForTurnNum > 0 && validForTurnNum < game.getTurnNum()) {
            discard();
            return false;
        }
        if (GameEvent.EventType.UNTAP_STEP.equals(event.getType()) 
                && game.getActivePlayerId().equals(source.getControllerId())) {            
            validForTurnNum = game.getTurnNum();
        }
        if (game.getTurn().getStepType() == PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
            if (!checkPlayableMode) {
                discard();
            }
            return true;
        }
        return false;
    }

}
