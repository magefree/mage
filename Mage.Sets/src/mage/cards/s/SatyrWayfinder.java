
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class SatyrWayfinder extends CardImpl {

    public SatyrWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Satyr Wayfinder enters the battlefield, reveal the top four cards of your library.
        // You may put a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.HAND, PutCards.GRAVEYARD)));
    }

    private SatyrWayfinder(final SatyrWayfinder card) {
        super(card);
    }

    @Override
    public SatyrWayfinder copy() {
        return new SatyrWayfinder(this);
    }
}
