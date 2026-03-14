package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;


public class AdditionalCombatPhaseEffect extends OneShotEffect {

    private final int additionalPhases;

    public AdditionalCombatPhaseEffect() {
        this(1);
    }

    public AdditionalCombatPhaseEffect(int additionalPhases) {
        super(Outcome.Benefit);
        if (additionalPhases < 1) {
            throw new IllegalArgumentException("Number of additional phases must be at least 1");
        }
        this.additionalPhases = additionalPhases;
        staticText = "after this phase, there " +
                (additionalPhases > 1 ? "are " + additionalPhases : "is an") +
                " additional combat phase" +
                (additionalPhases > 1 ? "s" : "");
    }

    protected AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
        super(effect);
        this.additionalPhases = effect.additionalPhases;
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
        return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < additionalPhases; i++) {
            TurnMod combat = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.COMBAT);
            game.getState().getTurnMods().add(combat);
        }
        return true;
    }
}
