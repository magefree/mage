
package mage.cards.f;

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
public final class FalkenrathReaver extends CardImpl {

    public FalkenrathReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private FalkenrathReaver(final FalkenrathReaver card) {
        super(card);
    }

    @Override
    public FalkenrathReaver copy() {
        return new FalkenrathReaver(this);
    }
}
