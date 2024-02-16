package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilhanaWayfinder extends CardImpl {

    public SilhanaWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Silhana Wayfinder enters the battlefield, look at the top four cards of your library.
        // You may reveal a creature or land card from among them and put it on top of your library.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, PutCards.TOP_ANY, PutCards.BOTTOM_RANDOM)));
    }

    private SilhanaWayfinder(final SilhanaWayfinder card) {
        super(card);
    }

    @Override
    public SilhanaWayfinder copy() {
        return new SilhanaWayfinder(this);
    }
}
