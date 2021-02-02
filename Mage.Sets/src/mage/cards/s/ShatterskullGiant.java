
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ShatterskullGiant extends CardImpl {

    public ShatterskullGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private ShatterskullGiant(final ShatterskullGiant card) {
        super(card);
    }

    @Override
    public ShatterskullGiant copy() {
        return new ShatterskullGiant(this);
    }
}
