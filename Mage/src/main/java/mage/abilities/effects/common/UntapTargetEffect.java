package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class UntapTargetEffect extends OneShotEffect {

    protected boolean useOnlyTargetPointer;

    public UntapTargetEffect() {
        this(true);
    }

    public UntapTargetEffect(boolean useOnlyTargetPointer) {
        super(Outcome.Untap);
        this.useOnlyTargetPointer = useOnlyTargetPointer;
    }

    public UntapTargetEffect(final UntapTargetEffect effect) {
        super(effect);
        this.useOnlyTargetPointer = effect.useOnlyTargetPointer;
    }

    @Override
    public UntapTargetEffect copy() {
        return new UntapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!useOnlyTargetPointer && source.getTargets().size() > 1) {
            source.getTargets().forEach((target) -> {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.untap(game);
                    }
                }
            });
        } else {
            for (UUID target : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.untap(game);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        if (mode.getTargets().isEmpty()) {
            return "untap target permanent"; // TODO: add exeption about missing targets setup?
        }

        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder();
        sb.append("untap ");
        if (target.getNumberOfTargets() == 0) {
            sb.append("up to ");
        }

        boolean haveTargetWord = target.getTargetName().contains("target");
        if (target.getMaxNumberOfTargets() > 1 || target.getNumberOfTargets() == 0) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
            sb.append(haveTargetWord ? " " : " target ");
            sb.append(target.getTargetName()).append('s');
        } else {
            sb.append(haveTargetWord ? "" : "target ");
            sb.append(target.getTargetName());
        }

        return sb.toString();
    }
}