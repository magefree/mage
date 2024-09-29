package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackBlockUnlessConditionSourceEffect extends RestrictionEffect {

    private final Condition condition;

    public CantAttackBlockUnlessConditionSourceEffect(Condition condition) {
        super(Duration.WhileOnBattlefield);
        this.condition = condition;
        staticText = "{this} can't attack or block unless " + condition.toString();
    }

    protected CantAttackBlockUnlessConditionSourceEffect(final CantAttackBlockUnlessConditionSourceEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId()) && !condition.apply(game, source);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackBlockUnlessConditionSourceEffect copy() {
        return new CantAttackBlockUnlessConditionSourceEffect(this);
    }
}
