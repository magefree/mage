package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class BookDevourer extends CardImpl {

    public BookDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Book Devourer deals combat damage to a player, you may discard all the cards in your hand. If you do, draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DiscardHandDrawSameNumberSourceEffect()
                        .setText("discard all the cards in your hand. "
                                + "If you do, draw that many cards"), true
        ));
    }

    private BookDevourer(final BookDevourer card) {
        super(card);
    }

    @Override
    public BookDevourer copy() {
        return new BookDevourer(this);
    }
}
