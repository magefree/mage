
package mage.cards.t;

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
public final class TobiasAndrion extends CardImpl {

    public TobiasAndrion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private TobiasAndrion(final TobiasAndrion card) {
        super(card);
    }

    @Override
    public TobiasAndrion copy() {
        return new TobiasAndrion(this);
    }
}
