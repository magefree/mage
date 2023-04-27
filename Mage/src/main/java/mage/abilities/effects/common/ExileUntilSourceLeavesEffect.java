package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class ExileUntilSourceLeavesEffect extends OneShotEffect {

    public ExileUntilSourceLeavesEffect() {
        super(Outcome.Removal);
    }

    public ExileUntilSourceLeavesEffect(final ExileUntilSourceLeavesEffect effect) {
        super(effect);
    }

    @Override
    public ExileUntilSourceLeavesEffect copy() {
        return new ExileUntilSourceLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        // If Banishing Light leaves the battlefield before its triggered ability resolves, the target permanent won't be exiled.
        if (permanent == null) {
            return false;
        }

        ExileTargetEffect effect = new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
        if (targetPointer != null) {  // Grasping Giant
            effect.setTargetPointer(targetPointer);
        }
        return effect.apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "exile " + getTargetPointer().describeTargets(mode.getTargets(), "that creature") + " until {this} leaves the battlefield";
    }
}
