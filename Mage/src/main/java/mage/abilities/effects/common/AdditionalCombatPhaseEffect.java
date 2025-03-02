package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;

import java.util.UUID;

public class AdditionalCombatPhaseEffect extends OneShotEffect {

    private final int additionalPhases;
    private final TargetController targetController;

    public AdditionalCombatPhaseEffect() {
        super(Outcome.Benefit);
        this.additionalPhases = 1;
        this.targetController = TargetController.YOU;
        staticText = "after this phase, there is an additional combat phase";
    }

    public AdditionalCombatPhaseEffect(int additionalPhases) {
        this(additionalPhases, TargetController.YOU);
    }

    public AdditionalCombatPhaseEffect(int additionalPhases, TargetController targetController) {
        super(Outcome.Benefit);
        if (additionalPhases < 1) {
            throw new IllegalArgumentException("Number of additional phases must be at least 1");
        }
        if (additionalPhases == 1) {
            this.additionalPhases = 1;
            staticText = "after this phase, there is an additional combat phase";
        } else {
            this.additionalPhases = additionalPhases;
            staticText = "after this phase, there are " + additionalPhases + " additional combat phases";
        }
        this.targetController = targetController;
    }

    protected AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
        super(effect);
        this.additionalPhases = effect.additionalPhases;
        this.targetController = effect.targetController;
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
        return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId;
        switch (targetController) {
            case YOU:
                controllerId = source.getControllerId();
                break;
            case ANY:
                controllerId = game.getActivePlayerId();
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in AdditionalCombatPhaseEffect: " + targetController);
        }
        for (int i = 0; i < additionalPhases; i++) {
            TurnMod combat = new TurnMod(controllerId).withExtraPhase(TurnPhase.COMBAT);
            game.getState().getTurnMods().add(combat);
        }
        return true;
    }
}
