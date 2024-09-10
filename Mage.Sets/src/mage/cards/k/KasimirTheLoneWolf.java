
package mage.cards.k;

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
public final class KasimirTheLoneWolf extends CardImpl {

    public KasimirTheLoneWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
    }

    private KasimirTheLoneWolf(final KasimirTheLoneWolf card) {
        super(card);
    }

    @Override
    public KasimirTheLoneWolf copy() {
        return new KasimirTheLoneWolf(this);
    }
}
