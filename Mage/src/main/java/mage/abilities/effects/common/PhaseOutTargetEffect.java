package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public class PhaseOutTargetEffect extends OneShotEffect {

    protected final String targetDescription;

    public PhaseOutTargetEffect() {
        this((String) null);
    }

    public PhaseOutTargetEffect(String targetDescription) {
        super(Outcome.Detriment);
        this.targetDescription = targetDescription;
    }

    private PhaseOutTargetEffect(final PhaseOutTargetEffect effect) {
        super(effect);
        this.targetDescription = effect.targetDescription;
    }

    @Override
    public PhaseOutTargetEffect copy() {
        return new PhaseOutTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it")
                + (getTargetPointer().isPlural(mode.getTargets()) ? " phase out" : " phases out");
    }
}
