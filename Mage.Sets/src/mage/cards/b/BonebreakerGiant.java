

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class BonebreakerGiant extends CardImpl {

    public BonebreakerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private BonebreakerGiant(final BonebreakerGiant card) {
        super(card);
    }

    @Override
    public BonebreakerGiant copy() {
        return new BonebreakerGiant(this);
    }

}
