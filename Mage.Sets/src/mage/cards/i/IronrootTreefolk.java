
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
public final class IronrootTreefolk extends CardImpl {

    public IronrootTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }

    private IronrootTreefolk(final IronrootTreefolk card) {
        super(card);
    }

    @Override
    public IronrootTreefolk copy() {
        return new IronrootTreefolk(this);
    }
}
