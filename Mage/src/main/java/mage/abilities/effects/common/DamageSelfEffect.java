
package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North, Quercitron
 */
public class DamageSelfEffect extends OneShotEffect {

    protected int amount;

    public DamageSelfEffect(int amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    protected DamageSelfEffect(final DamageSelfEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DamageSelfEffect copy() {
        return new DamageSelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{this} deals ").append(amount).append(" damage to itself");
        return sb.toString();
    }
}
