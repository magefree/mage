
package mage.cards.c;

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
public final class CrimsonKobolds extends CardImpl {

    public CrimsonKobolds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{0}");
        this.color.setRed(true);
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
    }

    private CrimsonKobolds(final CrimsonKobolds card) {
        super(card);
    }

    @Override
    public CrimsonKobolds copy() {
        return new CrimsonKobolds(this);
    }
}
