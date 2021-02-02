
package mage.cards.b;

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
public final class BlindPhantasm extends CardImpl {

    public BlindPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private BlindPhantasm(final BlindPhantasm card) {
        super(card);
    }

    @Override
    public BlindPhantasm copy() {
        return new BlindPhantasm(this);
    }
}
