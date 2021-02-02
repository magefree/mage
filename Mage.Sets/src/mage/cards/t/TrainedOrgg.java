
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
public final class TrainedOrgg extends CardImpl {

    public TrainedOrgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}");
        this.subtype.add(SubType.ORGG);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
    }

    private TrainedOrgg(final TrainedOrgg card) {
        super(card);
    }

    @Override
    public TrainedOrgg copy() {
        return new TrainedOrgg(this);
    }
}
