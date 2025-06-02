package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LumberingWorldwagon extends CardImpl {

    public LumberingWorldwagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // This Vehicle's power is equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(LandsYouControlCount.instance)));

        // Whenever this Vehicle enters or attacks, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), true));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private LumberingWorldwagon(final LumberingWorldwagon card) {
        super(card);
    }

    @Override
    public LumberingWorldwagon copy() {
        return new LumberingWorldwagon(this);
    }
}
