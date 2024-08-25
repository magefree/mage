package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
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
        Player targetController = null;
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            targetController = game.getPlayer(permanent.getControllerId());
        } else {
            Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                targetController = game.getPlayer(spell.getControllerId());
            }
        }
        if (targetController != null) {
            targetController.drawCards(amount.calculate(game, source, this), source, game);
            return true;
        }
        return false;
    }

}
