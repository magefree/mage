
package mage.cards.v;

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
public final class VastwoodGorger extends CardImpl {

    public VastwoodGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);
    }

    private VastwoodGorger(final VastwoodGorger card) {
        super(card);
    }

    @Override
    public VastwoodGorger copy() {
        return new VastwoodGorger(this);
    }
}
