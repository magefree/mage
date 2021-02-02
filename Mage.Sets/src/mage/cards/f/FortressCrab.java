
package mage.cards.f;

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
public final class FortressCrab extends CardImpl {

    public FortressCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(1);
        this.toughness = new MageInt(6);
    }

    private FortressCrab(final FortressCrab card) {
        super(card);
    }

    @Override
    public FortressCrab copy() {
        return new FortressCrab(this);
    }
}
