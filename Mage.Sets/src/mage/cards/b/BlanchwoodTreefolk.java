

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class BlanchwoodTreefolk extends CardImpl {

    public BlanchwoodTreefolk (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private BlanchwoodTreefolk(final BlanchwoodTreefolk card) {
        super(card);
    }

    @Override
    public BlanchwoodTreefolk copy() {
        return new BlanchwoodTreefolk(this);
    }
}
