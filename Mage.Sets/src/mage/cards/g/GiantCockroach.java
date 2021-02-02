
package mage.cards.g;

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
public final class GiantCockroach extends CardImpl {

    public GiantCockroach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private GiantCockroach(final GiantCockroach card) {
        super(card);
    }

    @Override
    public GiantCockroach copy() {
        return new GiantCockroach(this);
    }
}
