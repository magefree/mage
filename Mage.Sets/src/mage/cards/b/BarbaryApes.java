
package mage.cards.b;

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
public final class BarbaryApes extends CardImpl {

    public BarbaryApes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private BarbaryApes(final BarbaryApes card) {
        super(card);
    }

    @Override
    public BarbaryApes copy() {
        return new BarbaryApes(this);
    }
}
