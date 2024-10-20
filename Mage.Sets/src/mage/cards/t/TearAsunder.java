package mage.cards.t;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TearAsunder extends CardImpl {
    private static final FilterPermanent playableFilter = new FilterPermanent("artifact, enchantment, or nonland permanent");

    static {
        playableFilter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())
        ));
    }

    public TearAsunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));

        // Exile target artifact or enchantment. If this spell was kicked, exile target nonland permanent instead.
        this.getSpellAbility().addEffect(new ExileTargetEffect().setText("Exile target artifact or enchantment. If this spell was kicked, exile target nonland permanent instead."));
        this.getSpellAbility().addTarget(new TargetPermanent(playableFilter));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT), new TargetNonlandPermanent()));
    }

    private TearAsunder(final TearAsunder card) {
        super(card);
    }

    @Override
    public TearAsunder copy() {
        return new TearAsunder(this);
    }
}
