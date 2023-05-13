package mage.cards.s;

import mage.abilities.dynamicvalue.common.SnowManaSpentValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearchForGlory extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "snow permanent card, a legendary card, or a Saga card"
    );

    static {
        filter.add(SearchForGloryPredicate.instance);
    }

    public SearchForGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        this.supertype.add(SuperType.SNOW);

        // Search your library for a snow permanent card, a legendary card, or a Saga card, reveal it, put it into your hand, then shuffle your library. You gain 1 life for each {S} spent to cast this spell.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(
                SnowManaSpentValue.instance
        ).setText("You gain 1 life for each {S} spent to cast this spell"));
    }

    private SearchForGlory(final SearchForGlory card) {
        super(card);
    }

    @Override
    public SearchForGlory copy() {
        return new SearchForGlory(this);
    }
}

enum SearchForGloryPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return (input.isPermanent(game) && input.isSnow(game))
                || input.isLegendary(game)
                || input.hasSubtype(SubType.SAGA, game);
    }
}
