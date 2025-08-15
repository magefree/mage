package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class DamageWithExcessEffect extends OneShotEffect {

    private final DynamicValue amount;

    public DamageWithExcessEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DamageWithExcessEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        this.staticText = "{this} deals " + (amount instanceof StaticValue ? amount : "X") + " damage to target creature" +
                (amount instanceof StaticValue ? "" : ", where X is the number of " + amount.getMessage()) +
                ". Excess damage is dealt to that creature's controller instead";
    }

    private DamageWithExcessEffect(final DamageWithExcessEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageWithExcessEffect copy() {
        return new DamageWithExcessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || sourceObject == null) {
            return false;
        }
        int damage = amount.calculate(game, source, this);
        int excess = permanent.damageWithExcess(damage, source, game);
        if (excess > 0) {
            Optional.ofNullable(permanent)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(excess, source, game));
        }
        return true;
    }
}
