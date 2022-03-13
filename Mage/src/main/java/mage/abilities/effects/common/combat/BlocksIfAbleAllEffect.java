
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class BlocksIfAbleAllEffect extends RequirementEffect {

    private final FilterCreaturePermanent filter;

    public BlocksIfAbleAllEffect(FilterCreaturePermanent filter) {
        this(filter,Duration.WhileOnBattlefield);
    }

    public BlocksIfAbleAllEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        staticText = new StringBuilder(filter.getMessage())
                .append(" block ")
                .append(duration == Duration.EndOfTurn ? "this":"each")
                .append(" turn if able").toString();
        this.filter = filter;
    }
    public BlocksIfAbleAllEffect(final BlocksIfAbleAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public BlocksIfAbleAllEffect copy() {
        return new BlocksIfAbleAllEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
    
    @Override
    public boolean mustBlock(Game game) {
        return true;
    }
    
    @Override
    public boolean mustBlockAny(Game game) {
        return true;
    }
    
    @Override
    public boolean mustAttack(Game game) {
        return false;
    }



}
