package mage.cards.t;

import java.util.UUID;

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
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class TaintedTreats extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public TaintedTreats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");

        // Destroy target artifact or creature. If its mana value was 4 or less, create a Food token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new CreateTokenEffect(new FoodToken()), condition,
            "If its mana value was 4 or less, create a Food token"
        ));
    }

    private TaintedTreats(final TaintedTreats card) {
        super(card);
    }

    @Override
    public TaintedTreats copy() {
        return new TaintedTreats(this);
    }
}
