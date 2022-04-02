package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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

    public DamageAttachedEffect(final DamageAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageAttachedEffect copy() {
        return new DamageAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if(enchanted == null) {
            return false;
        }
        enchanted.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, true);
        return true;
    }
}
