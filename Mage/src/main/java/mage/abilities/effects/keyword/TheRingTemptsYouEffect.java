package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class TheRingTemptsYouEffect extends OneShotEffect {

    public TheRingTemptsYouEffect() {
        super(Outcome.Benefit);
        staticText = "the Ring tempts you";
    }

    private TheRingTemptsYouEffect(final TheRingTemptsYouEffect effect) {
        super(effect);
    }

    @Override
    public TheRingTemptsYouEffect copy() {
        return new TheRingTemptsYouEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.temptWithTheRing(source.getControllerId());
        return true;
    }
}
