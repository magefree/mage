
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Wehk
 */
public final class TransguildCourier extends CardImpl {

    public TransguildCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);
    }

    public TransguildCourier(final TransguildCourier card) {
        super(card);
    }

    @Override
    public TransguildCourier copy() {
        return new TransguildCourier(this);
    }
}
