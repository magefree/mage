

package mage.cards.l;

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
public final class LagacLizard extends CardImpl {

    public LagacLizard (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private LagacLizard(final LagacLizard card) {
        super(card);
    }

    @Override
    public LagacLizard copy() {
        return new LagacLizard(this);
    }

}
