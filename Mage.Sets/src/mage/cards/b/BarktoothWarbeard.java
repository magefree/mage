
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class BarktoothWarbeard extends CardImpl {

    public BarktoothWarbeard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
    }

    private BarktoothWarbeard(final BarktoothWarbeard card) {
        super(card);
    }

    @Override
    public BarktoothWarbeard copy() {
        return new BarktoothWarbeard(this);
    }
}
