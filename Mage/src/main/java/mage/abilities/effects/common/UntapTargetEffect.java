package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class UntapTargetEffect extends OneShotEffect {

    public UntapTargetEffect() {
        this((String) null);
    }

    public UntapTargetEffect(String text) {
        super(Outcome.Untap);
        if (text != null) {
            this.staticText = text;
        }
    }

    protected UntapTargetEffect(final UntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public UntapTargetEffect copy() {
        return new UntapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.untap(game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "untap " + getTargetPointer().describeTargets(mode.getTargets(), "that permanent");
    }
}
