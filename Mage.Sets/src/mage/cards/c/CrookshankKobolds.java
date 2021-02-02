
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
public final class CrookshankKobolds extends CardImpl {

    public CrookshankKobolds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{0}");
        this.color.setRed(true);
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
    }

    private CrookshankKobolds(final CrookshankKobolds card) {
        super(card);
    }

    @Override
    public CrookshankKobolds copy() {
        return new CrookshankKobolds(this);
    }
}
