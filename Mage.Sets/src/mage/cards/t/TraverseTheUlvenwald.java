package mage.cards.t;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TraverseTheUlvenwald extends CardImpl {

    private static final String rule = "Search your library for a basic land card, reveal it, put it into your hand, then shuffle.<br>" +
            AbilityWord.DELIRIUM.formatWord() + "If " + DeliriumCondition.instance.toString() +
            ", instead search your library for a creature or land card, reveal it, put it into your hand, then shuffle.";

    public TraverseTheUlvenwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND), true),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true),
                DeliriumCondition.instance, rule
        ));
    }

    private TraverseTheUlvenwald(final TraverseTheUlvenwald card) {
        super(card);
    }

    @Override
    public TraverseTheUlvenwald copy() {
        return new TraverseTheUlvenwald(this);
    }
}
