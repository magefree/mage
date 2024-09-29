package mage.abilities.effects.common;

import mage.abilities.Ability;
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
public class DamageAttachedControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public DamageAttachedControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DamageAttachedControllerEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        this.staticText = "{this} deals " + amount + " damage to that creature's controller";
    }

    protected DamageAttachedControllerEffect(final DamageAttachedControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageAttachedControllerEffect copy() {
        return new DamageAttachedControllerEffect(this);
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
        Player player = game.getPlayer(enchanted.getControllerId());
        if (player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
