package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBlockAloneSourceEffect extends RestrictionEffect {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");
    public CantBlockAloneSourceEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block alone";
    }

    protected CantBlockAloneSourceEffect(final CantBlockAloneSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBlockAloneSourceEffect copy() {
        return new CantBlockAloneSourceEffect(this);
    }

    @Override
    public boolean canBlockCheckAfter(Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            game.debugMessage(permanent.getIdName()+" sees "+game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).size()+" blocking");
            return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).size() <= 1;
        }
        return false;
    }
}
