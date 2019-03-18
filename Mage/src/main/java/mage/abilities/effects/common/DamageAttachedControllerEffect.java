

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
 *
 * @author LoneFox
 */
public class DamageAttachedControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public DamageAttachedControllerEffect(int amount) {
        super(Outcome.Damage);
        this.amount = new StaticValue(amount);
    }
    
    public DamageAttachedControllerEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public DamageAttachedControllerEffect(final DamageAttachedControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageAttachedControllerEffect copy() {
        return new DamageAttachedControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if(enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if(enchanted == null) {
            return false;
        }
        Player player = game.getPlayer(enchanted.getControllerId());
        if(player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if ("equal to".equals(amount.toString())) {
            return "{this} deals damage " + amount + " that creatures toughness to that creature's controller";
        }
         return "{this} deals " + amount + " damage to that creature's controller";
    }
}
