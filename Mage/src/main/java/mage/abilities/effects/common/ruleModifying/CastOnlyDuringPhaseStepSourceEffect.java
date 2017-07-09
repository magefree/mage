/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class CastOnlyDuringPhaseStepSourceEffect extends ContinuousRuleModifyingEffectImpl {

    private final TurnPhase turnPhase;
    private final PhaseStep phaseStep;
    private final Condition condition;

    public CastOnlyDuringPhaseStepSourceEffect(TurnPhase turnPhase, PhaseStep phaseStep, Condition condition) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.turnPhase = turnPhase;
        this.phaseStep = phaseStep;
        this.condition = condition;
        staticText = setText();
    }

    private CastOnlyDuringPhaseStepSourceEffect(final CastOnlyDuringPhaseStepSourceEffect effect) {
        super(effect);
        this.turnPhase = effect.turnPhase;
        this.phaseStep = effect.phaseStep;
        this.condition = effect.condition;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // has to return true, if the spell cannot be cast in the current phase / step
        if (event.getSourceId().equals(source.getSourceId())) {
            if ((turnPhase != null && game.getPhase().getType() != turnPhase)
                    || (phaseStep != null && (game.getTurn().getStepType() != phaseStep))
                    || (condition != null && !condition.apply(game, source))) {
                return true;
            }
        }
        return false; // cast not prevented by this effect
    }

    @Override
    public CastOnlyDuringPhaseStepSourceEffect copy() {
        return new CastOnlyDuringPhaseStepSourceEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("cast {this} only during ");
        if (turnPhase != null) {
            sb.append(turnPhase.toString());
        }
        if (phaseStep != null) {
            sb.append("the ").append(phaseStep.getStepText());
        }
        if (condition != null) {
            sb.append(' ').append(condition.toString());
        }
        return sb.toString();
    }
}
