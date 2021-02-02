
package mage.cards.d;

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
public final class DakmorScorpion extends CardImpl {

    public DakmorScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SCORPION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private DakmorScorpion(final DakmorScorpion card) {
        super(card);
    }

    @Override
    public DakmorScorpion copy() {
        return new DakmorScorpion(this);
    }
}
