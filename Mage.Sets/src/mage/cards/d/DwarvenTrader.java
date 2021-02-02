
package mage.cards.d;

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
public final class DwarvenTrader extends CardImpl {

    public DwarvenTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private DwarvenTrader(final DwarvenTrader card) {
        super(card);
    }

    @Override
    public DwarvenTrader copy() {
        return new DwarvenTrader(this);
    }
}
