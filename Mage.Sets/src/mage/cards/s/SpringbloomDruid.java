package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpringbloomDruid extends CardImpl {

    public SpringbloomDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Springbloom Druid enters the battlefield, you may sacrifice a land. If you do, search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                        0, 2, StaticFilters.FILTER_CARD_BASIC_LAND
                ), true
                ).setText("search your library for up to two basic land cards, " +
                        "put them onto the battlefield tapped, then shuffle"
                ), new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)
        )));
    }

    private SpringbloomDruid(final SpringbloomDruid card) {
        super(card);
    }

    @Override
    public SpringbloomDruid copy() {
        return new SpringbloomDruid(this);
    }
}
