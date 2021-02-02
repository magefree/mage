
package mage.cards.i;

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
public final class IndomitableAncients extends CardImpl {

    public IndomitableAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(10);
    }

    private IndomitableAncients(final IndomitableAncients card) {
        super(card);
    }

    @Override
    public IndomitableAncients copy() {
        return new IndomitableAncients(this);
    }
}
