package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;

/**
 * @author balazskristof
 */
public class AdditionalEndStepEffect extends OneShotEffect {

    private final int additionalSteps;

    public AdditionalEndStepEffect() {
        this(1);
    }

    public AdditionalEndStepEffect(int additionalSteps) {
        this(additionalSteps, false);
    }

    public AdditionalEndStepEffect(boolean afterTextAtStart) {
        this(1, afterTextAtStart);
    }

    public AdditionalEndStepEffect(int additionalSteps, boolean afterTextAtStart) {
        super(Outcome.Benefit);
        if (additionalSteps < 1) {
            throw new IllegalArgumentException("Number of additional steps must be at least 1");
        }
        this.additionalSteps = additionalSteps;
        if (additionalSteps == 1) {
            staticText = (afterTextAtStart ? "after this step, " : "") + "there is an additional end step" + ((afterTextAtStart ? "" : "after this step"));
        } else {
            staticText = (afterTextAtStart ? "after this step, " : "") + "there are " + additionalSteps + " additional end steps" + ((afterTextAtStart ? "" : " after this step"));
        }
    }

    protected AdditionalEndStepEffect(final AdditionalEndStepEffect effect) {
        super(effect);
        this.additionalSteps = effect.additionalSteps;
    }

    @Override
    public AdditionalEndStepEffect copy() {
        return new AdditionalEndStepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < additionalSteps; i++) {
            TurnMod end = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.END);
            game.getState().getTurnMods().add(end);
        }
        return true;
    }
}
