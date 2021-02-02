
package mage.cards.k;

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
public final class KrovikanScoundrel extends CardImpl {

    public KrovikanScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private KrovikanScoundrel(final KrovikanScoundrel card) {
        super(card);
    }

    @Override
    public KrovikanScoundrel copy() {
        return new KrovikanScoundrel(this);
    }
}
