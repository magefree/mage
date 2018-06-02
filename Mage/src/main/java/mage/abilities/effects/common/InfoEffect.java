

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class InfoEffect extends OneShotEffect {

    public InfoEffect(String text) {
        super(Outcome.Neutral);
        this.staticText = text;
    }

    public InfoEffect(final InfoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public InfoEffect copy() {
        return new InfoEffect(this);
    }

}
