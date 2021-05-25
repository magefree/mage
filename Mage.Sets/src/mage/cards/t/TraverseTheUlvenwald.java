package mage.cards.t;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TraverseTheUlvenwald extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or land card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public TraverseTheUlvenwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new InvertCondition(DeliriumCondition.instance),
                "Search your library for a basic land card, reveal it, put it into your hand, then shuffle."));

        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead search your library
        // for a creature or land card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, filter), true),
                DeliriumCondition.instance,
                "<br><i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead search your library for a creature or land card, "
                        + "reveal it, put it into your hand, then shuffle."));
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private TraverseTheUlvenwald(final TraverseTheUlvenwald card) {
        super(card);
    }

    @Override
    public TraverseTheUlvenwald copy() {
        return new TraverseTheUlvenwald(this);
    }
}
