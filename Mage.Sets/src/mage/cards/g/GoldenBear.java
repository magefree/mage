
package mage.cards.g;

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
public final class GoldenBear extends CardImpl {

    public GoldenBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private GoldenBear(final GoldenBear card) {
        super(card);
    }

    @Override
    public GoldenBear copy() {
        return new GoldenBear(this);
    }
}
