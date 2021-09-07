package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelestusSanctifier extends CardImpl {

    public CelestusSanctifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // If it's neither day nor night, it becomes day as Celestus Sanctifier enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, look at the top two cards of your library. Put one of them into your graveyard.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new LookLibraryAndPickControllerEffect(
                StaticValue.get(2), false, StaticValue.get(1), StaticFilters.FILTER_CARD,
                Zone.LIBRARY, true, false, false, Zone.GRAVEYARD, false
        ).setText("look at the top two cards of your library. Put one of them into your graveyard")));
    }

    private CelestusSanctifier(final CelestusSanctifier card) {
        super(card);
    }

    @Override
    public CelestusSanctifier copy() {
        return new CelestusSanctifier(this);
    }
}
