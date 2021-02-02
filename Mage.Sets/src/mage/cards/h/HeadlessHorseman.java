
package mage.cards.h;

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
public final class HeadlessHorseman extends CardImpl {

    public HeadlessHorseman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private HeadlessHorseman(final HeadlessHorseman card) {
        super(card);
    }

    @Override
    public HeadlessHorseman copy() {
        return new HeadlessHorseman(this);
    }
}
