
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PygmyRazorback extends CardImpl {

    public PygmyRazorback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private PygmyRazorback(final PygmyRazorback card) {
        super(card);
    }

    @Override
    public PygmyRazorback copy() {
        return new PygmyRazorback(this);
    }
}
