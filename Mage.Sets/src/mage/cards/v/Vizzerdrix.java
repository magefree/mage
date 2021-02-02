
package mage.cards.v;

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
public final class Vizzerdrix extends CardImpl {

    public Vizzerdrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
    }

    private Vizzerdrix(final Vizzerdrix card) {
        super(card);
    }

    @Override
    public Vizzerdrix copy() {
        return new Vizzerdrix(this);
    }
}
