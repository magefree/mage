package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * The creatures defined by filter can't attack any opponent
 *
 * @author LevelX2
 */
public class CantAttackAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantAttackAllEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        StringBuilder sb = new StringBuilder(filter.getMessage()).append(" can't attack");
        if (!duration.toString().isEmpty()) {
            if (duration == Duration.EndOfTurn) {
                sb.append(" this turn");
            } else {
                sb.append(' ').append(duration.toString());
            }
        }
        staticText = sb.toString();
    }

    protected CantAttackAllEffect(final CantAttackAllEffect effect) {
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
    public CantAttackAllEffect copy() {
        return new CantAttackAllEffect(this);
    }

}
