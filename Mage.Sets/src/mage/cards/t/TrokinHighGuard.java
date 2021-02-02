
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TrokinHighGuard extends CardImpl {

    public TrokinHighGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private TrokinHighGuard(final TrokinHighGuard card) {
        super(card);
    }

    @Override
    public TrokinHighGuard copy() {
        return new TrokinHighGuard(this);
    }
}
