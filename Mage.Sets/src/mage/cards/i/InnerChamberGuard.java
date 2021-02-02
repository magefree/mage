
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class InnerChamberGuard extends CardImpl {

    public InnerChamberGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.addAbility(new BushidoAbility(2));
    }

    private InnerChamberGuard(final InnerChamberGuard card) {
        super(card);
    }

    @Override
    public InnerChamberGuard copy() {
        return new InnerChamberGuard(this);
    }
}
