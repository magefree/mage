
package mage.cards.f;

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
public final class FemerefScouts extends CardImpl {

    public FemerefScouts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
    }

    private FemerefScouts(final FemerefScouts card) {
        super(card);
    }

    @Override
    public FemerefScouts copy() {
        return new FemerefScouts(this);
    }
}
