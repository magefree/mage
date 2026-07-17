package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class BecomePreparedTargetEffect extends OneShotEffect {

    private final boolean prepared;

    public BecomePreparedTargetEffect(boolean prepared) {
        super(Outcome.Benefit);
        this.prepared = prepared;
    }

    private BecomePreparedTargetEffect(final BecomePreparedTargetEffect effect) {
        super(effect);
        this.prepared = effect.prepared;
    }

    @Override
    public BecomePreparedTargetEffect copy() {
        return new BecomePreparedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.setPrepared(prepared, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return this.getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + " becomes " + (prepared ? "" : "un") + "prepared";
    }
}
