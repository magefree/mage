package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.UUID;

/**
 * @author fireshoes
 */
public class PhaseOutTargetEffect extends OneShotEffect {

    protected String targetDescription;
    protected boolean useOnlyTargetPointer;

    public PhaseOutTargetEffect() {
        super(Outcome.Detriment);
    }

    public PhaseOutTargetEffect(String targetDescription, boolean useOnlyTargetPointer) {
        super(Outcome.Detriment);
        this.targetDescription = targetDescription;
        this.useOnlyTargetPointer = useOnlyTargetPointer;
    }

    public PhaseOutTargetEffect(final PhaseOutTargetEffect effect) {
        super(effect);
        this.targetDescription = effect.targetDescription;
        this.useOnlyTargetPointer = effect.useOnlyTargetPointer;
    }

    @Override
    public PhaseOutTargetEffect copy() {
        return new PhaseOutTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!useOnlyTargetPointer && source.getTargets().size() > 1) {
            for (Target target : source.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.phaseOut(game);
                    }
                }
            }
            return true;
        }
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

        StringBuilder sb = new StringBuilder();
        if (targetDescription != null && !targetDescription.isEmpty()) {
            sb.append(targetDescription);
        } else {
            sb.append("Target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" phase");
        if (mode.getTargets().isEmpty()
                || mode.getTargets().get(0).getMaxNumberOfTargets() <= 1) {
            sb.append('s');
        }
        sb.append(" out");
        return sb.toString();
    }

}
