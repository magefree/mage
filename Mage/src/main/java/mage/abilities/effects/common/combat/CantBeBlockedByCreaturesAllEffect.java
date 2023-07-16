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
public class CantBeBlockedByCreaturesAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterBlockedBy;
    private final FilterCreaturePermanent filterCreatures;

    public CantBeBlockedByCreaturesAllEffect(FilterCreaturePermanent filterCreatures, FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterCreatures = filterCreatures;
        this.filterBlockedBy = filterBlockedBy;
        staticText = filterCreatures.getMessage() + " can't be blocked "
                + (duration == Duration.EndOfTurn ? "this turn " : "")
                + (filterBlockedBy.getMessage().startsWith("except by") ? "" : "by ")
                + filterBlockedBy.getMessage();
    }

    public CantBeBlockedByCreaturesAllEffect(final CantBeBlockedByCreaturesAllEffect effect) {
        super(effect);
        this.filterCreatures = effect.filterCreatures;
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filterCreatures.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filterBlockedBy.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByCreaturesAllEffect copy() {
        return new CantBeBlockedByCreaturesAllEffect(this);
    }
}
