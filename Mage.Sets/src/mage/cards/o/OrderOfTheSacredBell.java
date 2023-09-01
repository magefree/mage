

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class OrderOfTheSacredBell extends CardImpl {

    public OrderOfTheSacredBell (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private OrderOfTheSacredBell(final OrderOfTheSacredBell card) {
        super(card);
    }

    @Override
    public OrderOfTheSacredBell copy() {
        return new OrderOfTheSacredBell(this);
    }

}
