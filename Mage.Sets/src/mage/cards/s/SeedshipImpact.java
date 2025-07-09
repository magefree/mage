package mage.cards.s;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.LanderToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SeedshipImpact extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public SeedshipImpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Destroy target artifact or enchantment. If its mana value was 2 or less, create a Lander token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new LanderToken()), condition,
                "If its mana value was 2 or less, create a Lander token. " + LanderToken.getReminderText()
        ));
    }

    private SeedshipImpact(final SeedshipImpact card) {
        super(card);
    }

    @Override
    public SeedshipImpact copy() {
        return new SeedshipImpact(this);
    }
}
