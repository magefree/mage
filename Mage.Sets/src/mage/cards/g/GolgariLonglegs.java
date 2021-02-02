
package mage.cards.g;

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
public final class GolgariLonglegs extends CardImpl {

    public GolgariLonglegs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B/G}{B/G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private GolgariLonglegs(final GolgariLonglegs card) {
        super(card);
    }

    @Override
    public GolgariLonglegs copy() {
        return new GolgariLonglegs(this);
    }
}
