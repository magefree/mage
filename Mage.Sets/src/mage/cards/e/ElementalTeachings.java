package mage.cards.e;

import mage.abilities.effects.common.search.SearchLibraryForFourDifferentCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalTeachings extends CardImpl {

    public ElementalTeachings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        this.subtype.add(SubType.LESSON);

        // Search your library for up to four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryForFourDifferentCardsEffect(
                StaticFilters.FILTER_CARD_LANDS, PutCards.BATTLEFIELD_TAPPED, false
        ));
    }

    private ElementalTeachings(final ElementalTeachings card) {
        super(card);
    }

    @Override
    public ElementalTeachings copy() {
        return new ElementalTeachings(this);
    }
}
