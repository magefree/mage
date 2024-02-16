package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * The source of this effect can't attack any opponent
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantAttackAnyPlayerSourceEffect extends RestrictionEffect {

    public CantAttackAnyPlayerSourceEffect(Duration duration) {
        super(duration);
    }

    protected CantAttackAnyPlayerSourceEffect(final CantAttackAnyPlayerSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackAnyPlayerSourceEffect copy() {
        return new CantAttackAnyPlayerSourceEffect(this);
    }

}
