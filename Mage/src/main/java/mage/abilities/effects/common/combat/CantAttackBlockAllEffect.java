package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackBlockAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantAttackBlockAllEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        StringBuilder sb = new StringBuilder(filter.getMessage()).append(" can't attack or block");
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append(" this turn");
            } else {
                sb.append(' ').append(duration.toString());
            }
        }
        staticText = sb.toString();
    }

    public CantAttackBlockAllEffect(final CantAttackBlockAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackBlockAllEffect copy() {
        return new CantAttackBlockAllEffect(this);
    }

}
