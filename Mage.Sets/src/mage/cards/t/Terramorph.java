package mage.cards.t;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Terramorph extends CardImpl {

    public Terramorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for a basic land card, put it into the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), false
        ));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private Terramorph(final Terramorph card) {
        super(card);
    }

    @Override
    public Terramorph copy() {
        return new Terramorph(this);
    }
}
