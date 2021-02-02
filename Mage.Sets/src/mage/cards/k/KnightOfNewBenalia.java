
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class KnightOfNewBenalia extends CardImpl {

    public KnightOfNewBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private KnightOfNewBenalia(final KnightOfNewBenalia card) {
        super(card);
    }

    @Override
    public KnightOfNewBenalia copy() {
        return new KnightOfNewBenalia(this);
    }
}
