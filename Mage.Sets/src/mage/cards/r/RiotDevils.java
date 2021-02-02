
package mage.cards.r;

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
public final class RiotDevils extends CardImpl {

    public RiotDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private RiotDevils(final RiotDevils card) {
        super(card);
    }

    @Override
    public RiotDevils copy() {
        return new RiotDevils(this);
    }
}
