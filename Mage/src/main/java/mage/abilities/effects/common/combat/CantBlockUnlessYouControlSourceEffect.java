package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBlockUnlessYouControlSourceEffect extends RestrictionEffect {

    private final FilterControlledPermanent filter;

    public CantBlockUnlessYouControlSourceEffect(FilterControlledPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = "{this} can't block unless you control " + filter.getMessage();
    }

    public CantBlockUnlessYouControlSourceEffect(final CantBlockUnlessYouControlSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CantBlockUnlessYouControlSourceEffect copy() {
        return new CantBlockUnlessYouControlSourceEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId())
                && game.getBattlefield().count(filter, source.getControllerId(), source, game) == 0;
    }
}
