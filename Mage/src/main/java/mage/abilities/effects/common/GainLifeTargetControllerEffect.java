package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author xenohedron
 */
public class GainLifeTargetControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public GainLifeTargetControllerEffect(int amount) {
        this(StaticValue.get(amount));
        this.staticText = "its controller gains " + amount + " life";
    }

    public GainLifeTargetControllerEffect(DynamicValue amount) {
        super(Outcome.GainLife);
        this.amount = amount;
    }

    protected GainLifeTargetControllerEffect(final GainLifeTargetControllerEffect effect) {
        super(effect);
        amount = effect.amount.copy();
    }

    @Override
    public GainLifeTargetControllerEffect copy() {
        return new GainLifeTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetController = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (targetController != null) {
            targetController.gainLife(amount.calculate(game, source, this), game, source);
            return true;
        }
        return false;
    }

}
