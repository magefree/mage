
package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class YavimayaGranger extends CardImpl {


    public YavimayaGranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new EchoAbility("{2}{G}"));
        //When Yavimaya Granger enters the battlefield, you may search your library for a basic land card,
        //put that card onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true), true));
    }

    private YavimayaGranger(final YavimayaGranger card) {
        super(card);
    }

    @Override
    public YavimayaGranger copy() {
        return new YavimayaGranger(this);
    }
}
