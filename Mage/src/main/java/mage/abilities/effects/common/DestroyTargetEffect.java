package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DestroyTargetEffect extends OneShotEffect {

    protected boolean noRegen;
    protected boolean multitargetHandling;

    public DestroyTargetEffect() {
        this(false);
    }

    public DestroyTargetEffect(String ruleText) {
        this(ruleText, false);
    }

    public DestroyTargetEffect(boolean noRegen) {
        this(noRegen, false);
    }

    public DestroyTargetEffect(String ruleText, boolean noRegen) {
        this(noRegen, false);
        staticText = ruleText;
    }

    public DestroyTargetEffect(boolean noRegen, boolean multitargetHandling) {
        super(Outcome.Detriment);
        this.noRegen = noRegen;
        this.multitargetHandling = multitargetHandling;
    }

    public DestroyTargetEffect(final DestroyTargetEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
        this.multitargetHandling = effect.multitargetHandling;
    }

    @Override
    public DestroyTargetEffect copy() {
        return new DestroyTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (multitargetHandling
                && source.getTargets().size() > 1
                && targetPointer instanceof FirstTargetPointer) { // Decimate
            for (Target target : source.getTargets()) {
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null
                            && permanent.isPhasedIn()
                            && !permanent.isPhasedOutIndirectly()) {
                        permanent.destroy(source, game, noRegen);
                        affectedTargets++;
                    }
                }
            }
        } else {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null
                        && permanent.isPhasedIn()
                        && !permanent.isPhasedOutIndirectly()) {
                    permanent.destroy(source, game, noRegen);
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("destroy that creature"); //TODO add possibility to specify text with targetPointer usage
        } else {
            Target target;
            if (targetPointer instanceof SecondTargetPointer && mode.getTargets().size() > 1) {
                target = mode.getTargets().get(1);
            } else {
                target = mode.getTargets().get(0);
            }
            if (target.getNumberOfTargets() == 1) {
                String targetName = target.getTargetName();
                sb.append("destroy ");
                if (!targetName.startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(targetName);
            } else {
                if (target.getMaxNumberOfTargets() == target.getMinNumberOfTargets()) {
                    sb.append("destroy ").append(CardUtil.numberToText(target.getNumberOfTargets()));
                } else {
                    sb.append("destroy up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
                }
                sb.append(" target ").append(target.getTargetName());
            }
        }
        if (noRegen) {
            sb.append(". It can't be regenerated");
        }
        return sb.toString();
    }

}
