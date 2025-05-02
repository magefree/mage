package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author North
 */
public class LoseLifeTargetControllerEffect extends OneShotEffect {

    private final DynamicValue amount;

    public LoseLifeTargetControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeTargetControllerEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "Its controller loses " + amount + " life";
    }

    protected LoseLifeTargetControllerEffect(final LoseLifeTargetControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeTargetControllerEffect copy() {
        return new LoseLifeTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetController = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (targetController != null) {
            targetController.loseLife(amount.calculate(game, source, this), game, source, false);
            return true;
        }
        return false;
    }
}
