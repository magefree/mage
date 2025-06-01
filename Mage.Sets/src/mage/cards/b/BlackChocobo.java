package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackChocobo extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BIRD, "Birds");

    public BlackChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.nightCard = true;
        this.color.setGreen(true);

        // When this permanent transforms into Black Chocobo, search your library for a land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new TransformIntoSourceTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true)
        ));

        // Landfall -- Whenever a land you control enters, Birds you control get +1/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter, false
        )));
    }

    private BlackChocobo(final BlackChocobo card) {
        super(card);
    }

    @Override
    public BlackChocobo copy() {
        return new BlackChocobo(this);
    }
}
