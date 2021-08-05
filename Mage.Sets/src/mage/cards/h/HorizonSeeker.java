package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.BoastAbility;
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
public final class HorizonSeeker extends CardImpl {

    public HorizonSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Boast — {1}{G}: Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new BoastAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), "{1}{G}"));
    }

    private HorizonSeeker(final HorizonSeeker card) {
        super(card);
    }

    @Override
    public HorizonSeeker copy() {
        return new HorizonSeeker(this);
    }
}
