package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class DamageTargetControllerEffect extends OneShotEffect {

    protected final DynamicValue amount;
    protected final boolean preventable;
    protected final String name;

    public DamageTargetControllerEffect(int amount) {
        this(StaticValue.get(amount), true);
    }

    public DamageTargetControllerEffect(int amount, String name) {
        this(StaticValue.get(amount), true, name);
    }

    public DamageTargetControllerEffect(int amount, boolean preventable) {
        this(StaticValue.get(amount), preventable);
    }

    public DamageTargetControllerEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetControllerEffect(DynamicValue amount, String name) {
        this(amount, true, name);
    }

    public DamageTargetControllerEffect(DynamicValue amount, boolean preventable) {
        this(amount, preventable, "creature");
    }

    public DamageTargetControllerEffect(DynamicValue amount, boolean preventable, String name) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
        this.name = name;
    }

    protected DamageTargetControllerEffect(final DamageTargetControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.preventable = effect.preventable;
        this.name = effect.name;
    }

    @Override
    public DamageTargetControllerEffect copy() {
        return new DamageTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetController = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (targetController != null) {
            targetController.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String message = amount.getMessage();
        if (message.isEmpty()) {
            message = amount.toString();
        }
        return "{this} deals " + message + " damage to that "
                + name + "'s controller"
                + (preventable ? "" : ". The damage can't be prevented");
    }
}
