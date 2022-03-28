package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class CantAttackYouAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterAttacker;
    private final boolean alsoPlaneswalker;

    public CantAttackYouAllEffect(Duration duration) {
        this(duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public CantAttackYouAllEffect(Duration duration, FilterCreaturePermanent filter) {
        this(duration, filter, false);
    }

    public CantAttackYouAllEffect(Duration duration, FilterCreaturePermanent filter, boolean alsoPlaneswalker) {
        super(duration, Outcome.Benefit);
        this.filterAttacker = filter;
        this.alsoPlaneswalker = alsoPlaneswalker;
        staticText = filterAttacker.getMessage() + " can't attack you"
                + (alsoPlaneswalker ? " or a planeswalker you control" : "")
                + (duration == Duration.UntilYourNextTurn || duration == Duration.UntilEndOfYourNextTurn ? " " + duration.toString() : "");
    }

    CantAttackYouAllEffect(final CantAttackYouAllEffect effect) {
        super(effect);
        this.filterAttacker = effect.filterAttacker;
        this.alsoPlaneswalker = effect.alsoPlaneswalker;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filterAttacker.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (alsoPlaneswalker) {
            Permanent planeswalker = game.getPermanent(defenderId);
            if (planeswalker != null) {
                defenderId = planeswalker.getControllerId();
            }
        }
        return !defenderId.equals(source.getControllerId());
    }

    @Override
    public CantAttackYouAllEffect copy() {
        return new CantAttackYouAllEffect(this);
    }
}
