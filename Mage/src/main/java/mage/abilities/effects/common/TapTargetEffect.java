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
public class TapTargetEffect extends OneShotEffect {

    public TapTargetEffect() {
        this((String) null);
    }

    public TapTargetEffect(String text) {
        super(Outcome.Tap);
        if (text != null) {
            this.staticText = text;
        }
    }

    public TapTargetEffect(final TapTargetEffect effect) {
        super(effect);
    }

    @Override
    public TapTargetEffect copy() {
        return new TapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.tap(source, game);
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
            return "tap target permanent";
        }

        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1 || target.getNumberOfTargets() == 0) {
            if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
                return "tap " + CardUtil.numberToText(target.getNumberOfTargets()) + " target " + target.getTargetName() + 's';
            } else {
                return "tap up to " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + " target " + target.getTargetName() + (target.getMaxNumberOfTargets() > 1 ? "s" : "");
            }
        } else if (target.getMaxNumberOfTargets() == 0) {
            return "tap X target " + mode.getTargets().get(0).getTargetName();
        } else {
            return "tap target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
