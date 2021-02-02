
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TrainedJackal extends CardImpl {

    public TrainedJackal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.JACKAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private TrainedJackal(final TrainedJackal card) {
        super(card);
    }

    @Override
    public TrainedJackal copy() {
        return new TrainedJackal(this);
    }
}
