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
        this.staticCantBeBlockedMessage = "can't be blocked "
                + (duration == Duration.EndOfTurn ? "this turn " : "")
                + (filter.getMessage().startsWith("except by") ? "" : "by ")
                + (filter.getMessage());
        staticText = "{this} " + this.staticCantBeBlockedMessage;
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
