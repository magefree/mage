package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummaryJudgment extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SummaryJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Summary Judgement deals 3 damage to target tapped creature.
        // Addendum — If you cast this spell during your main phase, it deals 5 damage to that creature instead.
        this.getSpellAbility().addEffect(new SummaryJudgementEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SummaryJudgment(final SummaryJudgment card) {
        super(card);
    }

    @Override
    public SummaryJudgment copy() {
        return new SummaryJudgment(this);
    }
}

class SummaryJudgementEffect extends OneShotEffect {

    SummaryJudgementEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to target tapped creature." +
                "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, " +
                "it deals 5 damage instead.";
    }

    private SummaryJudgementEffect(final SummaryJudgementEffect effect) {
        super(effect);
    }

    @Override
    public SummaryJudgementEffect copy() {
        return new SummaryJudgementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int damage = 3;
        if (AddendumCondition.instance.apply(game, source)) {
            damage = 5;
        }
        return permanent.damage(damage, source.getSourceId(), source, game) > 0;
    }
}
