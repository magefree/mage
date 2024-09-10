package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManyPartings extends CardImpl {

    public ManyPartings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle. Create a Food token.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
    }

    private ManyPartings(final ManyPartings card) {
        super(card);
    }

    @Override
    public ManyPartings copy() {
        return new ManyPartings(this);
    }
}
