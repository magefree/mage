
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
public final class RottedHystrix extends CardImpl {

    public RottedHystrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
    }

    private RottedHystrix(final RottedHystrix card) {
        super(card);
    }

    @Override
    public RottedHystrix copy() {
        return new RottedHystrix(this);
    }
}
