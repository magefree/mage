package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class HeapedHarvest extends CardImpl {

    public HeapedHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.FOOD);

        // When Heaped Harvest enters and when you sacrifice it, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new OrTriggeredAbility(Zone.ALL, new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true), true,
                "When {this} enters and when you sacrifice it, ",
                new EntersBattlefieldTriggeredAbility(null), new SacrificeSourceTriggeredAbility(null)
        ));

        // {2}, {T}, Sacrifice Heaped Harvest: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private HeapedHarvest(final HeapedHarvest card) {
        super(card);
    }

    @Override
    public HeapedHarvest copy() {
        return new HeapedHarvest(this);
    }
}
