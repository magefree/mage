package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ConniveTargetEffect extends OneShotEffect {

    public ConniveTargetEffect() {
        super(Outcome.Benefit);
    }

    private ConniveTargetEffect(final ConniveTargetEffect effect) {
        super(effect);
    }

    @Override
    public ConniveTargetEffect copy() {
        return new ConniveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            ConniveSourceEffect.connive(game.getPermanent(targetId), 1, source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return this.getTargetPointer().describeTargets(mode.getTargets(), "it") + " connives";
    }
}
