package mage.cards.c;

import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CycleOfRenewal extends CardImpl {

    public CycleOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        this.subtype.add(SubType.LESSON);

        // Sacrifice a land. Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, ""));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_BASIC_LAND
        ), true));
    }

    private CycleOfRenewal(final CycleOfRenewal card) {
        super(card);
    }

    @Override
    public CycleOfRenewal copy() {
        return new CycleOfRenewal(this);
    }
}
