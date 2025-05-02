package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LoneFox
 */
public class DamageAttachedEffect extends OneShotEffect {

    protected DynamicValue amount;

    public DamageAttachedEffect(int amount) {
        this(StaticValue.get(amount), "{this}");
    }

    public DamageAttachedEffect(int amount, String whoDealDamageName) {
        this(StaticValue.get(amount), whoDealDamageName);
    }

    public DamageAttachedEffect(DynamicValue amount) {
        this(amount, "{this}");
    }

    public DamageAttachedEffect(DynamicValue amount, String whoDealDamageName) {
        super(Outcome.Damage);
        this.amount = amount;
        this.staticText = whoDealDamageName + " deals " + amount + " damage to enchanted creature";
    }

    protected DamageAttachedEffect(final DamageAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageAttachedEffect copy() {
        return new DamageAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentOrLKI(game);
        if (enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if (enchanted == null) {
            return false;
        }
        enchanted.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, true);
        return true;
    }
}
