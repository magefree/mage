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

    public DestroyTargetEffect() {
        this(false);
    }

    public DestroyTargetEffect(String ruleText) {
        this(ruleText, false);
    }

    public DestroyTargetEffect(boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.noRegen = noRegen;
    }

    public DestroyTargetEffect(String ruleText, boolean noRegen) {
        this(noRegen);
        staticText = ruleText;
    }

    public DestroyTargetEffect(final DestroyTargetEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public DestroyTargetEffect copy() {
        return new DestroyTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null
                    && permanent.isPhasedIn()
                    && !permanent.isPhasedOutIndirectly()) {
                permanent.destroy(source, game, noRegen);
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("destroy " + getTargetPointer().describeTargets(mode.getTargets(), "that creature"));
        if (noRegen) {
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ? ". They" : ". It");
            sb.append(" can't be regenerated");
        }
        return sb.toString();
    }
}
