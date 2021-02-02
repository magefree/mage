
package mage.cards.b;

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
public final class BalduvianBears extends CardImpl {

    public BalduvianBears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private BalduvianBears(final BalduvianBears card) {
        super(card);
    }

    @Override
    public BalduvianBears copy() {
        return new BalduvianBears(this);
    }
}
