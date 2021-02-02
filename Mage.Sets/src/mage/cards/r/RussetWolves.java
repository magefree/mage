
package mage.cards.r;

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
public final class RussetWolves extends CardImpl {

    public RussetWolves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private RussetWolves(final RussetWolves card) {
        super(card);
    }

    @Override
    public RussetWolves copy() {
        return new RussetWolves(this);
    }
}
