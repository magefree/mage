
package mage.cards.b;

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
public final class BearCub extends CardImpl {

    public BearCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private BearCub(final BearCub card) {
        super(card);
    }

    @Override
    public BearCub copy() {
        return new BearCub(this);
    }
}
