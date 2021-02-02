
package mage.cards.n;

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
public final class NessianCourser extends CardImpl {

    public NessianCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private NessianCourser(final NessianCourser card) {
        super(card);
    }

    @Override
    public NessianCourser copy() {
        return new NessianCourser(this);
    }
}
