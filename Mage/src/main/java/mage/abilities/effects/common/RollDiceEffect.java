package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author spjspj
 */
public class RollDiceEffect extends OneShotEffect {

    protected int numSides;

    public RollDiceEffect(int numSides) {
        super(Outcome.Benefit);
        this.numSides = numSides;
    }

    protected RollDiceEffect(final RollDiceEffect effect) {
        super(effect);
        this.numSides = effect.numSides;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.rollDice(outcome, source, game, numSides);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return "roll a " + CardUtil.numberToText(numSides) + "-sided die";
    }

    @Override
    public RollDiceEffect copy() {
        return new RollDiceEffect(this);
    }
}
