package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class DamageTargetControllerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean preventable;

    public DamageTargetControllerEffect(int amount) {
        this(StaticValue.get(amount), true);
    }

    public DamageTargetControllerEffect(int amount, boolean preventable) {
        this(StaticValue.get(amount), preventable);
    }

    public DamageTargetControllerEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetControllerEffect(DynamicValue amount, boolean preventable) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
    }

    protected DamageTargetControllerEffect(final DamageTargetControllerEffect effect) {
        super(effect);
        amount = effect.amount.copy();
        preventable = effect.preventable;
    }

    @Override
    public DamageTargetControllerEffect copy() {
        return new DamageTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player targetController = game.getPlayer(permanent.getControllerId());
            if (targetController != null) {
                targetController.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} deals " + amount.getMessage() + " damage to "
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + "'s controller"
                + (preventable ? "" : ". The damage can't be prevented");
    }
}
