
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
public final class FlamebornViron extends CardImpl {

    public FlamebornViron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private FlamebornViron(final FlamebornViron card) {
        super(card);
    }

    @Override
    public FlamebornViron copy() {
        return new FlamebornViron(this);
    }
}
