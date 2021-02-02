
package mage.cards.a;

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
public final class AxegrinderGiant extends CardImpl {

    public AxegrinderGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private AxegrinderGiant(final AxegrinderGiant card) {
        super(card);
    }

    @Override
    public AxegrinderGiant copy() {
        return new AxegrinderGiant(this);
    }
}
