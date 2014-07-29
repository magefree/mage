package mage.abilities.effects.common;

import mage.MageObject;
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
        super(Duration.Custom, Outcome.Detriment, false, true);
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
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "{this} doesn't untap (" + mageObject.getLogName() + ")";
        }
        return null;
    }

    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // the check for turn number is needed if multiple effects are added to prevent untap in next untap step
        // if we don't check for turn number, every turn only one effect would be used instead of correctly only one time
        // to skip the untap effect.
        
        // Discard effect if related to previous turn
        if (validForTurnNum > 0 && validForTurnNum < game.getTurnNum()) {
            discard();
            return false;
        }
        // remember the turn of the untap step the effect has to be applied
        if (GameEvent.EventType.UNTAP_STEP.equals(event.getType()) 
                && game.getActivePlayerId().equals(source.getControllerId())) {
            if (validForTurnNum == game.getTurnNum()) { // the turn has a secon untap step but the effect is already related to the first untap step
                discard();
                return false;                
            }
            validForTurnNum = game.getTurnNum();
        }
        // skip untap action
        if (game.getTurn().getStepType() == PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
                discard();
            return true;
        }
        return false;
    }

}
