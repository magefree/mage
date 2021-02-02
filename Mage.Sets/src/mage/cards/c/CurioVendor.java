
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CurioVendor extends CardImpl {

    public CurioVendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private CurioVendor(final CurioVendor card) {
        super(card);
    }

    @Override
    public CurioVendor copy() {
        return new CurioVendor(this);
    }
}
