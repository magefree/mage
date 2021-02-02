
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TalasMerchant extends CardImpl {

    public TalasMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private TalasMerchant(final TalasMerchant card) {
        super(card);
    }

    @Override
    public TalasMerchant copy() {
        return new TalasMerchant(this);
    }
}
