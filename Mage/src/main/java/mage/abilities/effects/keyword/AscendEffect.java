
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AscendAbility;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class AscendEffect extends OneShotEffect {

    public AscendEffect() {
        super(Outcome.Detriment);
        staticText = AscendAbility.ASCEND_RULE + "<br>";
    }

    protected AscendEffect(final AscendEffect effect) {
        super(effect);
    }

    @Override
    public AscendEffect copy() {
        return new AscendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AscendAbility.checkAscend(game, source, true)) {
            AscendAbility.applyAscend(game, game.getPlayer(source.getControllerId()));
            return true;
        }
        return false;
    }
}
