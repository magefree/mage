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
            targetController.gainLife(amount.calculate(game, source, this), game, source);
            return true;
        }
        return false;
    }

}
