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
public class CantBeBlockedByCreaturesAllEffect extends EvasionEffect {

    private final FilterCreaturePermanent filterBlockedBy;
    private final FilterCreaturePermanent filterCreatures;

    public CantBeBlockedByCreaturesAllEffect(FilterCreaturePermanent filterCreatures, FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterCreatures = filterCreatures;
        this.filterBlockedBy = filterBlockedBy;
        this.staticCantBeBlockedMessage =
                new StringBuilder("can't be blocked ")
                        .append(duration == Duration.EndOfTurn ? "this turn " : "")
                        .append(filterBlockedBy.getMessage().startsWith("except by") ? "" : "by ")
                        .append(filterBlockedBy.getMessage())
                        .toString();

        staticText =
                new StringBuilder(filterCreatures.getMessage())
                        .append(" ")
                        .append(this.staticCantBeBlockedMessage)
                        .toString();
    }

    protected CantBeBlockedByCreaturesAllEffect(final CantBeBlockedByCreaturesAllEffect effect) {
        super(effect);
        this.filterCreatures = effect.filterCreatures;
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filterCreatures.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return filterBlockedBy.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByCreaturesAllEffect copy() {
        return new CantBeBlockedByCreaturesAllEffect(this);
    }
}
