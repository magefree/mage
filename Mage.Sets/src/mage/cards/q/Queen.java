package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Queen extends CardImpl {

    public Queen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Queen enters the battlefield, reveal the top four cards of your library. Put all Zerg creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
    }

    public Queen(final Queen card) {
        super(card);
    }

    @Override
    public Queen copy() {
        return new Queen(this);
    }
}
