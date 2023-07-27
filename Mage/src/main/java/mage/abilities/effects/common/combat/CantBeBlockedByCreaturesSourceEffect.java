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
public class CantBeBlockedByCreaturesSourceEffect extends EvasionEffect {

    private final FilterCreaturePermanent filter;

    public CantBeBlockedByCreaturesSourceEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        this.staticCantBeBlockedMessage =
                new StringBuilder("can't be blocked ")
                        .append(duration == Duration.EndOfTurn ? "this turn " : "")
                        .append(filter.getMessage().startsWith("except by") ? "" : "by ")
                        .append(filter.getMessage())
                        .toString();
        staticText =
                new StringBuilder("{this} ")
                        .append(this.staticCantBeBlockedMessage)
                        .toString();
    }

    protected CantBeBlockedByCreaturesSourceEffect(final CantBeBlockedByCreaturesSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return filter.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByCreaturesSourceEffect copy() {
        return new CantBeBlockedByCreaturesSourceEffect(this);
    }
}
