package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class TakeTheInitiativeEffect extends OneShotEffect {

    public TakeTheInitiativeEffect() {
        super(Outcome.Benefit);
        staticText = "you take the initiative";
    }

    private TakeTheInitiativeEffect(final TakeTheInitiativeEffect effect) {
        super(effect);
    }

    @Override
    public TakeTheInitiativeEffect copy() {
        return new TakeTheInitiativeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.takeInitiative(source, source.getControllerId());
        return true;
    }
}
