package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;

public class DontUntapInControllersNextUntapStepSourceEffect extends ContinuousRuleModifyingEffectImpl {

    private int validForTurnNum;
    
    public DontUntapInControllersNextUntapStepSourceEffect() {
        super(Duration.Custom, Outcome.Detriment, false, true);
        staticText = "{this} doesn't untap during your next untap step";
        validForTurnNum = 0;
    }

    public DontUntapInControllersNextUntapStepSourceEffect(final DontUntapInControllersNextUntapStepSourceEffect effect) {
        super(effect);
    }

    @Override
    public DontUntapInControllersNextUntapStepSourceEffect copy() {
        return new DontUntapInControllersNextUntapStepSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "{this} doesn't untap (" + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP || event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // the check for turn number is needed if multiple effects are added to prevent untap in next untap step
        // if we don't check for turn number, every turn only one effect would be used instead of correctly consuming
        // all existing skip the next untap step effects.
        
        // Discard effect if related to a previous turn
        if (validForTurnNum > 0 && validForTurnNum < game.getTurnNum()) {
            discard();
            return false;
        }
        // remember the turn of the untap step the effect has to be applied
        if (event.getType() == GameEvent.EventType.UNTAP_STEP
                && game.isActivePlayer(source.getControllerId())) {
            if (validForTurnNum == game.getTurnNum()) { // the turn has a second untap step but the effect is already related to the first untap step
                discard();
                return false;                
            }
            validForTurnNum = game.getTurnNum();
        }
        // skip untap action
        if (game.getTurnStepType() == PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && game.isActivePlayer(source.getControllerId())
                && event.getTargetId().equals(source.getSourceId())) {
                discard();
            return true;
        }
        return false;
    }

}
