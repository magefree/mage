

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
    private String sourceName = "{this}";

    public DamageAttachedEffect(int amount) {
        super(Outcome.Damage);
        this.amount = StaticValue.get(amount);
    }

    public DamageAttachedEffect(int amount, String whoDealDamageName) {
        this(amount);
        this.sourceName = whoDealDamageName;
    }
    
    public DamageAttachedEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public DamageAttachedEffect(final DamageAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.sourceName = effect.sourceName;
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if ("equal to".equals(amount.toString())) {
            return this.sourceName + " deals damage " + amount + " that creatures toughness to enchanted creature";
        }
         return this.sourceName + " deals " + amount + " damage to enchanted creature";
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
