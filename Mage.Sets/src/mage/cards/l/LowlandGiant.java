
package mage.cards.l;

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
public final class LowlandGiant extends CardImpl {

    public LowlandGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private LowlandGiant(final LowlandGiant card) {
        super(card);
    }

    @Override
    public LowlandGiant copy() {
        return new LowlandGiant(this);
    }
}
