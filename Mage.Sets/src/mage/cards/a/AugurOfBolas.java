package mage.cards.a;

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
 * @author awjackson
 */
public final class AugurOfBolas extends CardImpl {

    public AugurOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Augur of Bolas enters the battlefield, look at the top three cards of your library.
        // You may reveal an instant or sorcery card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, PutCards.HAND, PutCards.BOTTOM_ANY)));
    }

    private AugurOfBolas(final AugurOfBolas card) {
        super(card);
    }

    @Override
    public AugurOfBolas copy() {
        return new AugurOfBolas(this);
    }
}
