package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBeBlockedByAllSourceEffect extends EvasionEffect {

    private final FilterCreaturePermanent filterBlockedBy;

    public CantBeBlockedByAllSourceEffect(FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterBlockedBy = filterBlockedBy;

        this.staticCantBeBlockedMessage =
                new StringBuilder("can't be blocked")
                        .append((filterBlockedBy.getMessage().startsWith("except by") ? "" : "by "))
                        .append(filterBlockedBy.getMessage())
                        .toString();
        this.staticText =
                new StringBuilder("{this} ")
                        .append(this.staticCantBeBlockedMessage)
                        .toString();
    }

    protected CantBeBlockedByAllSourceEffect(final CantBeBlockedByAllSourceEffect effect) {
        super(effect);
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.equals(source.getSourcePermanentIfItStillExists(game));
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return filterBlockedBy.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByAllSourceEffect copy() {
        return new CantBeBlockedByAllSourceEffect(this);
    }
}
