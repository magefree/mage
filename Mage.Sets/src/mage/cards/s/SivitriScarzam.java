
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class SivitriScarzam extends CardImpl {

    public SivitriScarzam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private SivitriScarzam(final SivitriScarzam card) {
        super(card);
    }

    @Override
    public SivitriScarzam copy() {
        return new SivitriScarzam(this);
    }
}
