

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RhoxBrute extends CardImpl {

    public RhoxBrute (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private RhoxBrute(final RhoxBrute card) {
        super(card);
    }

    @Override
    public RhoxBrute copy() {
        return new RhoxBrute(this);
    }
}
