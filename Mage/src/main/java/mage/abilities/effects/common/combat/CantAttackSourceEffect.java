

package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com & L_J
 */
public class CantAttackSourceEffect extends RestrictionEffect {

    public CantAttackSourceEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can't attack";
    }

    public CantAttackSourceEffect(final CantAttackSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public CantAttackSourceEffect copy() {
        return new CantAttackSourceEffect(this);
    }

}
