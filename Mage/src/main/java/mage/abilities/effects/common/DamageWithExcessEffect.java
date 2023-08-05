package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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
        this.staticText = "{this} deals " + amount + " damage to target creature" +
                (amount instanceof StaticValue ? "" : ", where X is " + amount.getMessage()) +
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
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || sourceObject == null) {
            return false;
        }
        int damage = amount.calculate(game, source, this);
        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        lethal = Math.min(lethal, damage);
        permanent.damage(lethal, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null && lethal < damage) {
            player.damage(damage - lethal, source.getSourceId(), source, game);
        }
        return true;
    }
}
