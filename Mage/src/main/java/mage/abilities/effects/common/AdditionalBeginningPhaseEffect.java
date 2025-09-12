package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;


public class AdditionalBeginningPhaseEffect extends OneShotEffect {

    private final int additionalPhases;

    public AdditionalBeginningPhaseEffect() {
        super(Outcome.Benefit);
        this.additionalPhases = 1;
        staticText = "after this phase, there is an additional beginning phase";
    }

    public AdditionalBeginningPhaseEffect(int additionalPhases) {
        super(Outcome.Benefit);
        if (additionalPhases < 1) {
            throw new IllegalArgumentException("Number of additional phases must be at least 1");
        }
        if (additionalPhases == 1) {
            this.additionalPhases = 1;
            staticText = "after this phase, there is an additional beginning phase";
        } else {
            this.additionalPhases = additionalPhases;
            staticText = "after this phase, there are " + additionalPhases + " additional beginning phases";
        }
    }

    protected AdditionalBeginningPhaseEffect(final AdditionalBeginningPhaseEffect effect) {
        super(effect);
        this.additionalPhases = effect.additionalPhases;
    }

    @Override
    public AdditionalBeginningPhaseEffect copy() {
        return new AdditionalBeginningPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < additionalPhases; i++) {
            TurnMod beginning = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.BEGINNING);
            game.getState().getTurnMods().add(beginning);
        }
        return true;
    }
}
