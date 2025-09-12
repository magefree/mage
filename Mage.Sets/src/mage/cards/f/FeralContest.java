package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FeralContest extends CardImpl {

    public FeralContest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put a +1/+1 counter on target creature you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));

        // Another target creature blocks it this turn if able.
        this.getSpellAbility().addEffect(new FeralContestEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("to force to block").setTargetTag(2));
    }

    private FeralContest(final FeralContest card) {
        super(card);
    }

    @Override
    public FeralContest copy() {
        return new FeralContest(this);
    }
}

class FeralContestEffect extends RequirementEffect {

    FeralContestEffect() {
        this(Duration.EndOfTurn);
    }

    public FeralContestEffect(Duration duration) {
        super(duration);
        staticText = "Another target creature blocks it this turn if able";
    }

    private FeralContestEffect(final FeralContestEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getTargets().get(1).getFirstTarget())) {
            return permanent.canBlock(source.getFirstTarget(), game);
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
        return source.getFirstTarget();
    }

    @Override
    public FeralContestEffect copy() {
        return new FeralContestEffect(this);
    }

}
