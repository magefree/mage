package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreefolkReachToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TendTheSprigs extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.TREEFOLK.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 6);
    private static final Hint hint = new ValueHint(
            "Lands and Treefolk you control", new PermanentsOnBattlefieldCount(filter)
    );

    public TendTheSprigs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle. Then if you control seven or more lands and/or Treefolk, create a 3/4 green Treefolk creature token with reach.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
        );
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new TreefolkReachToken()), condition, "Then if you control seven " +
                "or more lands and/or Treefolk, create a 3/4 green Treefolk creature token with reach"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private TendTheSprigs(final TendTheSprigs card) {
        super(card);
    }

    @Override
    public TendTheSprigs copy() {
        return new TendTheSprigs(this);
    }
}
