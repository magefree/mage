package mage.cards.b;

import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.SamuraiToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanishingSlash extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, enchantment, or tapped creature");

    static {
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                CardType.ARTIFACT.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        TappedPredicate.TAPPED
                )
        ));
    }

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT),
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT)
    );
    private static final Hint hint1 = new ConditionHint(new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
    ), "You control an artifact");
    private static final Hint hint2 = new ConditionHint(new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT
    ), "You control an enchantment");

    public BanishingSlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}");

        // Destroy up to one target artifact, enchantment, or tapped creature. Then if you control an artifact and an enchantment, create a 2/2 white Samurai creature token with vigilance.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SamuraiToken()), condition, "Then if you control " +
                "an artifact and an enchantment, create a 2/2 white Samurai creature token with vigilance"
        ));
        this.getSpellAbility().addHint(hint1);
        this.getSpellAbility().addHint(hint2);
    }

    private BanishingSlash(final BanishingSlash card) {
        super(card);
    }

    @Override
    public BanishingSlash copy() {
        return new BanishingSlash(this);
    }
}
