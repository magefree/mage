package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class DrawCardTargetControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public DrawCardTargetControllerEffect(int amount) {
        this(StaticValue.get(amount));
        this.staticText = "its controller draws " + CardUtil.numberToText(amount, "a")
                + (amount == 1 ? " card" : " cards");
    }

    public DrawCardTargetControllerEffect(DynamicValue amount) {
        super(Outcome.DrawCard);
        this.amount = amount;
    }

    protected DrawCardTargetControllerEffect(final DrawCardTargetControllerEffect effect) {
        super(effect);
        amount = effect.amount.copy();
    }

    @Override
    public DrawCardTargetControllerEffect copy() {
        return new DrawCardTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetController = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (targetController != null) {
            targetController.drawCards(amount.calculate(game, source, this), source, game);
            return true;
        }
        return false;
    }

}
