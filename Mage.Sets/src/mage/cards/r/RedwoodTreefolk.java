
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author noxx
 */
public final class RedwoodTreefolk extends CardImpl {

    public RedwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
    }

    private RedwoodTreefolk(final RedwoodTreefolk card) {
        super(card);
    }

    @Override
    public RedwoodTreefolk copy() {
        return new RedwoodTreefolk(this);
    }
}
