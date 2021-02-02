
package mage.cards.h;

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
public final class HollowhengeBeast extends CardImpl {

    public HollowhengeBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    private HollowhengeBeast(final HollowhengeBeast card) {
        super(card);
    }

    @Override
    public HollowhengeBeast copy() {
        return new HollowhengeBeast(this);
    }
}
