package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnFromTheWilds extends CardImpl {

    public ReturnFromTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, false
        ));

        // * Create a 1/1 white Human creature token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new HumanToken())));

        // * Create a Food token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new FoodToken())));
    }

    private ReturnFromTheWilds(final ReturnFromTheWilds card) {
        super(card);
    }

    @Override
    public ReturnFromTheWilds copy() {
        return new ReturnFromTheWilds(this);
    }
}
