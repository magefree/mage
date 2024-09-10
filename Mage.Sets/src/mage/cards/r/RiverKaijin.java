

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
public final class RiverKaijin extends CardImpl {

    public RiverKaijin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
    }

    private RiverKaijin(final RiverKaijin card) {
        super(card);
    }

    @Override
    public RiverKaijin copy() {
        return new RiverKaijin(this);
    }

}
