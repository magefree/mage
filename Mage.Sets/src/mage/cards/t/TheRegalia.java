package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRegalia extends CardImpl {

    public TheRegalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever The Regalia attacks, reveal cards from the top of your library until you reveal a land card. Put that card onto the battlefield tapped and the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_LAND_A, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private TheRegalia(final TheRegalia card) {
        super(card);
    }

    @Override
    public TheRegalia copy() {
        return new TheRegalia(this);
    }
}
