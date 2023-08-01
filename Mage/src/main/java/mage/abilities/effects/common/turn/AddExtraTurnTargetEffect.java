
package mage.abilities.effects.common.turn;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;

/**
 * @author noxx
 */
public class AddExtraTurnTargetEffect extends OneShotEffect {

    public AddExtraTurnTargetEffect() {
        super(Outcome.ExtraTurn);
        staticText = "Target player takes an extra turn after this one";
    }

    public AddExtraTurnTargetEffect(final AddExtraTurnTargetEffect effect) {
        super(effect);
    }

    @Override
    public AddExtraTurnTargetEffect copy() {
        return new AddExtraTurnTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.getTargetPointer().getFirst(game, source) != null) {
            game.getState().getTurnMods().add(new TurnMod(this.getTargetPointer().getFirst(game, source)).withExtraTurn());
        }
        return true;
    }
}