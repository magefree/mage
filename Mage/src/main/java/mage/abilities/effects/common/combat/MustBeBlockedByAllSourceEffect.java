package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MustBeBlockedByAllSourceEffect extends RequirementEffect {

    private final FilterCreaturePermanent filter;

    public MustBeBlockedByAllSourceEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public MustBeBlockedByAllSourceEffect(Duration duration) {
        this(duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public MustBeBlockedByAllSourceEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        staticText = "All " + filter.getMessage() + " able to block {this}"
                + (duration.equals(Duration.EndOfTurn) ? " this turn " : " ") + "do so";
    }

    public MustBeBlockedByAllSourceEffect(final MustBeBlockedByAllSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (sourceCreature != null
                && sourceCreature.isAttacking()
                && filter.match(permanent, game)) {
            return permanent.canBlock(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public MustBeBlockedByAllSourceEffect copy() {
        return new MustBeBlockedByAllSourceEffect(this);
    }

}
