
package mage.cards.v;

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
public final class Vorstclaw extends CardImpl {

    public Vorstclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
    }

    private Vorstclaw(final Vorstclaw card) {
        super(card);
    }

    @Override
    public Vorstclaw copy() {
        return new Vorstclaw(this);
    }
}
