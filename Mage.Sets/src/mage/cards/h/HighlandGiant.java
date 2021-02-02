
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class HighlandGiant extends CardImpl {

    public HighlandGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private HighlandGiant(final HighlandGiant card) {
        super(card);
    }

    @Override
    public HighlandGiant copy() {
        return new HighlandGiant(this);
    }
}
