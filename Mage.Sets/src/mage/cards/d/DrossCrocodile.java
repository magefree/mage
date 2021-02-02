
package mage.cards.d;

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
public final class DrossCrocodile extends CardImpl {

    public DrossCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);
    }

    private DrossCrocodile(final DrossCrocodile card) {
        super(card);
    }

    @Override
    public DrossCrocodile copy() {
        return new DrossCrocodile(this);
    }
}
