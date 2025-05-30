package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class CowardsCantBlockWarriorsEffect extends RestrictionEffect {

    public CowardsCantBlockWarriorsEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Cowards can't block Warriors";
    }

    private CowardsCantBlockWarriorsEffect(final CowardsCantBlockWarriorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return attacker == null
                || blocker == null
                || !attacker.hasSubtype(SubType.WARRIOR, game)
                || !blocker.hasSubtype(SubType.COWARD, game);
    }

    @Override
    public CowardsCantBlockWarriorsEffect copy() {
        return new CowardsCantBlockWarriorsEffect(this);
    }
}
