
package mage.cards.a;

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
public final class AmphinCutthroat extends CardImpl {

    public AmphinCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private AmphinCutthroat(final AmphinCutthroat card) {
        super(card);
    }

    @Override
    public AmphinCutthroat copy() {
        return new AmphinCutthroat(this);
    }
}
