
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class SirShandlarOfEberyn extends CardImpl {

    public SirShandlarOfEberyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);
    }

    private SirShandlarOfEberyn(final SirShandlarOfEberyn card) {
        super(card);
    }

    @Override
    public SirShandlarOfEberyn copy() {
        return new SirShandlarOfEberyn(this);
    }
}
