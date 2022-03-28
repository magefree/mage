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
public class CantBeBlockedByCreaturesSourceEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantBeBlockedByCreaturesSourceEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        staticText = "{this} can't be blocked " + (duration == Duration.EndOfTurn ? "this turn " : "")
                + (filter.getMessage().startsWith("except by") ? "" : "by ") + filter.getMessage();
    }

    public CantBeBlockedByCreaturesSourceEffect(final CantBeBlockedByCreaturesSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filter.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByCreaturesSourceEffect copy() {
        return new CantBeBlockedByCreaturesSourceEffect(this);
    }
}
