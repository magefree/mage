
package mage.cards.t;

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
public final class TrainedArmodon extends CardImpl {

    public TrainedArmodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private TrainedArmodon(final TrainedArmodon card) {
        super(card);
    }

    @Override
    public TrainedArmodon copy() {
        return new TrainedArmodon(this);
    }
}
