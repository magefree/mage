
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
public final class TravelingPhilosopher extends CardImpl {

    public TravelingPhilosopher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private TravelingPhilosopher(final TravelingPhilosopher card) {
        super(card);
    }

    @Override
    public TravelingPhilosopher copy() {
        return new TravelingPhilosopher(this);
    }
}
