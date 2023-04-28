
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

public class EndTurnEffect extends OneShotEffect {

    public EndTurnEffect() {
        super(Outcome.Detriment);
        staticText = "End the turn. <i>(Exile all spells and abilities from the stack, including this card. The player whose turn it is discards down to their maximum hand size. Damage wears off, and \"this turn\" and \"until end of turn\" effects end.)</i>";
    }

    public EndTurnEffect(EndTurnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.isSimulation()) {
            game.informPlayers("The current turn ends");
        }
        return game.endTurn(source);
    }

    @Override
    public EndTurnEffect copy() {
        return new EndTurnEffect(this);
    }
}
