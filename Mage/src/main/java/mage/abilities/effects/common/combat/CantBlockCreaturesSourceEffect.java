package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author dustinconrad
 */
public class CantBlockCreaturesSourceEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantBlockCreaturesSourceEffect(FilterCreaturePermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    public CantBlockCreaturesSourceEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        staticText = "{this} can't block " + filter.getMessage();
    }

    public CantBlockCreaturesSourceEffect(CantBlockCreaturesSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return !filter.match(attacker, source.getControllerId(), source, game);
    }

    @Override
    public ContinuousEffect copy() {
        return new CantBlockCreaturesSourceEffect(this);
    }
}
