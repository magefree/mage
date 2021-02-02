
package mage.cards.n;

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
public final class NettleSwine extends CardImpl {

    public NettleSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private NettleSwine(final NettleSwine card) {
        super(card);
    }

    @Override
    public NettleSwine copy() {
        return new NettleSwine(this);
    }
}
