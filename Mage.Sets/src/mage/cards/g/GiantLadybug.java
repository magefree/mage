package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.ReachAbility;
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
public final class GiantLadybug extends CardImpl {

    public GiantLadybug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Giant Ladybug enters the battlefield, you may search your library for a basic land card, reveal it, then shuffle and put that card on top.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        ), true));
    }

    private GiantLadybug(final GiantLadybug card) {
        super(card);
    }

    @Override
    public GiantLadybug copy() {
        return new GiantLadybug(this);
    }
}
