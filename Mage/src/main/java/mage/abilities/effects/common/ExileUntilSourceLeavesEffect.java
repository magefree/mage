package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class ExileUntilSourceLeavesEffect extends OneShotEffect {

    public ExileUntilSourceLeavesEffect(String targetName) {
        super(Outcome.Removal);
        this.staticText = "exile target " + targetName + " an opponent controls until {this} leaves the battlefield";
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
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            ExileTargetEffect effect = new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
            if (targetPointer != null) {  // Grasping Giant
                effect.setTargetPointer(targetPointer);
            }
            return effect.apply(game, source);
        }
        return false;
    }
}
