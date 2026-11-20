package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class OberthClassStarship extends CardImpl {

    public OberthClassStarship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true
        )));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // 2/2
        this.addAbility(new StationLevelAbility(6).withLevelAbility(FlyingAbility.getInstance()).withPT(2, 2));
    }

    private OberthClassStarship(final OberthClassStarship card) {
        super(card);
    }

    @Override
    public OberthClassStarship copy() {
        return new OberthClassStarship(this);
    }
}
