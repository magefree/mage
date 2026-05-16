package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.PrepareUtil;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class UnprepareTargetEffect extends OneShotEffect {

    public UnprepareTargetEffect() {
        super(Outcome.Neutral);
        staticText = "that creature becomes unprepared";
    }

    private UnprepareTargetEffect(final UnprepareTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return PrepareUtil.unprepare(permanent, game);
    }

    @Override
    public UnprepareTargetEffect copy() {
        return new UnprepareTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return this.getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + " becomes unprepared";
    }
}
