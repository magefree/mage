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
public class CantBlockAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantBlockAllEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        this.staticText = filter.getMessage() + " can't block" + (duration == Duration.EndOfTurn ? " this turn" : "");
    }

    protected CantBlockAllEffect(final CantBlockAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantBlockAllEffect copy() {
        return new CantBlockAllEffect(this);
    }

}
