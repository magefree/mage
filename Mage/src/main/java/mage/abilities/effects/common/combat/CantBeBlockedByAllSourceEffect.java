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
public class CantBeBlockedByAllSourceEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterBlockedBy;

    public CantBeBlockedByAllSourceEffect(FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterBlockedBy = filterBlockedBy;
        staticText = "{this}"
                + " can't be blocked "
                + (filterBlockedBy.getMessage().startsWith("except by") ? "" : "by ")
                + filterBlockedBy.getMessage();
    }

    public CantBeBlockedByAllSourceEffect(final CantBeBlockedByAllSourceEffect effect) {
        super(effect);
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.equals(source.getSourcePermanentIfItStillExists(game));
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filterBlockedBy.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByAllSourceEffect copy() {
        return new CantBeBlockedByAllSourceEffect(this);
    }
}
