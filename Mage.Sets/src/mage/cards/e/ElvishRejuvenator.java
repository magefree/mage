package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class ElvishRejuvenator extends CardImpl {

    public ElvishRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elvish Rejuvenator enters the battlefield, look at the top five cards of your library.
        // You may put a land card from among them onto the battlefield tapped.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM)));
    }

    private ElvishRejuvenator(final ElvishRejuvenator card) {
        super(card);
    }

    @Override
    public ElvishRejuvenator copy() {
        return new ElvishRejuvenator(this);
    }
}
